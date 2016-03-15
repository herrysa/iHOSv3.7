package com.huge.ihos.period.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class PeriodAction
    extends JqGridBaseAction {
    private PeriodManager periodManager;

    private List periods;

    private Period period;

    private Long periodId;
    
    private String businessDate;
    
    private String currentPeriod;

    public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    public List getPeriods() {
        return periods;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare()
        throws Exception {
        super.prepare();
        HttpServletRequest req = this.getRequest();
        String ip = req.getRemoteAddr();
        String host = req.getRemoteHost();
        String u = req.getRemoteUser();
        int port = req.getRemotePort();
        if ( getRequest().getMethod().equalsIgnoreCase( "post" ) ) {
            // prevent failures on new
            String periodId = getRequest().getParameter( "period.periodId" );
            if ( periodId != null && !periodId.equals( "" ) ) {
                period = periodManager.get( new Long( periodId ) );
            }
        }
        this.clearSessionMessages();
    }

    /*
     * public String list() { periods = periodManager.search(query, Period.class); return SUCCESS; }
     */

    public void setPeriodId( Long periodId ) {
        this.periodId = periodId;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod( Period period ) {
        this.period = period;
    }

    public String delete() {
        periodManager.remove( period.getPeriodId() );
        saveMessage( getText( "period.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( periodId != null ) {
            period = periodManager.get( periodId );
        }
        else {
            period = new Period();
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( period.getPeriodId() == null );

        periodManager.save( period );

        String key = ( isNew ) ? "period.added" : "period.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String setCurrentPeriod() {
        try {
            Period sel = this.periodManager.get( periodId );
            this.periodManager.setCurrentPeriod( sel.getPeriodCode() );
//            this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
            this.currentPeriod = this.getLoginPeriod();
            return ajaxForward( true, "当前采集期间设置成功！", false );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            return ajaxForward( false, e.getMessage(), false );
        }

    }

    public String openDataPeriod() {

        try {

//            Period sel = this.periodManager.getCurrentDCPeriod();
        	String periodCode = this.getLoginPeriod();
            Period sel = this.periodManager.getPeriodByCode(periodCode);

            if ( sel == null || ( sel != null && sel.getPeriodId() == this.periodId ) ) {

                this.periodManager.openDataCollectionPeriod( this.periodManager.get( periodId ).getPeriodCode() );

                return ajaxForward( true, "当前采集期间打开成功！", false );
            }
            else {
                return ajaxForward( false, "采集期间打开失败.请检查.", false );
            }
        }
        catch ( Exception e ) {
            this.gridOperationMessage = e.getMessage();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    public String closeDataPeriod() {
        try {
//            Period sel = this.periodManager.getCurrentDCPeriod();
        	String periodCode = this.getLoginPeriod();
            Period sel = this.periodManager.getPeriodByCode(periodCode);
            if ( sel == null ) {
                return ajaxForward( false, "当前没有打开的数据采集期间.", false );
            }
            else if ( sel != null && sel.getPeriodId() != this.periodId ) {
                return ajaxForward( false, "选择有误,请检查.", false );

            }
            else {

                this.periodManager.closeDataCollectPeriod( sel.getPeriodCode() );
                return ajaxForward( true, "当前采集期间关闭成功！", false );
            }
        }
        catch ( Exception e ) {
            this.gridOperationMessage = e.getMessage();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    public String periodGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = periodManager.getPeriodCriteria( pagedRequests );
            this.periods = (List<Period>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "periodGridList Error", e );
        }
        return SUCCESS;
    }

    public String periodGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    periodManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "period.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                periodManager.save( period );
                String key = ( oper.equals( "add" ) ) ? "period.added" : "period.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );

            gridOperationMessage = "此期间正在被引用，不能被删除。";
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( period == null ) {
            return "Invalid period Data";
        }

        return SUCCESS;

    }

    private List allPeriodList;

    public List getAllPeriodList() {
        return allPeriodList;
    }

    public void setAllPeriodList( List allPeriodList ) {
        this.allPeriodList = allPeriodList;
    }

    public String allPeriodListSelect() {
        this.allPeriodList = this.periodManager.getAll();
        return SUCCESS;
    }
    
    public String getPeriodByBusinessDate(){
    	try {
    		currentPeriod = periodManager.getPeriodCodeByDate(DateUtil.convertStringToDate(businessDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return SUCCESS;
    }
}