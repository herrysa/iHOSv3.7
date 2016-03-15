package com.huge.ihos.system.datacollection.maptables.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.ihos.system.datacollection.maptables.service.AcctcostmapManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class AcctcostmapAction
    extends JqGridBaseAction
    {
    private AcctcostmapManager acctcostmapManager;

    private Long acctMapId;

    private List acctcostmaps;

    private Acctcostmap acctcostmap;

    private String acctcode;

    private String acctname;

    private String costitemid;

    private String costitem;

    public void setAcctcostmapManager( AcctcostmapManager acctcostmapManager ) {
        this.acctcostmapManager = acctcostmapManager;
    }

    public List getAcctcostmaps() {
        return acctcostmaps;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String acctcostmapId = getRequest().getParameter("acctcostmap.acctcode");
             if (acctcostmapId != null && !acctcostmapId.equals("")) {
                 acctcostmap = acctcostmapManager.get(new String(acctcostmapId));
             }
         }*/
        this.clearSessionMessages();
    }

    /*   public String list() {
           acctcostmaps = acctcostmapManager.search(query, Acctcostmap.class);
           return SUCCESS;
       }*/

    public void setAcctcode( String acctcode ) {
        this.acctcode = acctcode;
    }

    public Acctcostmap getAcctcostmap() {
        return acctcostmap;
    }

    public void setAcctcostmap( Acctcostmap acctcostmap ) {
        this.acctcostmap = acctcostmap;
    }

    public String delete() {
        acctcostmapManager.remove( acctcostmap.getAcctMapId() );
        saveMessage( getText( "acctcostmap.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( acctcode != null ) {
            acctcostmap = acctcostmapManager.get( acctMapId );
        }
        else {
            acctcostmap = new Acctcostmap();
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

        boolean isNew = ( acctcostmap.getAcctcode() == null );

        acctcostmapManager.save( acctcostmap );

        String key = ( isNew ) ? "acctcostmap.added" : "acctcostmap.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String acctcostmapGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = acctcostmapManager.getAppManagerCriteriaWithSearch( pagedRequests, Acctcostmap.class, filters );
            this.acctcostmaps = (List<Acctcostmap>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "acctcostmapGridList Error", e );
        }
        return SUCCESS;
    }

    public String acctcostmapGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer idsq = new StringTokenizer( id, "," );
                while ( idsq.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( idsq.nextToken() );
                    acctcostmapManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "acctcostmap.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
            	if ( (acctcostmapManager.existCode( "t_acctcostmap", "acctcode", acctcode, oper )&&oper.equals( "add" ))||(OtherUtil.measureNotNull(acctMapId)&&acctcostmapManager.existCodeEdit(acctMapId, "t_acctcostmap", "acctMapId", "acctcode", acctcode)) ) {
                    return ajaxForward( false, "您输入的编号已存在,请换一个!", false );
                }
                if ( acctcostmap == null ) {
                    if ( acctMapId == null )
                        acctMapId = 0L;
                    acctcostmap = new Acctcostmap();
                    acctcostmap.setAcctMapId( acctMapId == 0L ? null : acctMapId );
                    acctcostmap.setAcctcode( acctcode == null ? "" : acctcode );
                    acctcostmap.setAcctname( acctname == null ? "" : acctname );
                    acctcostmap.setCostitem( costitem == null ? "" : costitem );
                    acctcostmap.setCostitemid( costitemid == null ? "" : costitemid );
                }
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) && acctcostmap == null ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                acctcostmapManager.save( acctcostmap );
                String key = ( oper.equals( "add" ) ) ? "acctcostmap.added" : "acctcostmap.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return SUCCESS;
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( acctcostmap == null ) {
            return "Invalid acctcostmap Data";
        }

        return SUCCESS;

    }

    public Long getAcctMapId() {
        return acctMapId;
    }

    public void setAcctMapId( Long acctMapId ) {
        this.acctMapId = acctMapId;
    }

    public void setAcctname( String acctname ) {
        this.acctname = acctname;
    }

    public void setCostitemid( String costitemid ) {
        this.costitemid = costitemid;
    }

    public void setCostitem( String costitem ) {
        this.costitem = costitem;
    }
}