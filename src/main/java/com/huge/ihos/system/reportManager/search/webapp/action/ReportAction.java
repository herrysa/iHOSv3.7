package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.ihos.system.reportManager.search.service.ReportManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class ReportAction
    extends BaseAction
     {
    private ReportManager reportManager;

    private List reports;

    private Report report;

    private Long reportId;

    private PagerFactory pagerFactory;

    // entity paging
    protected Integer page = 0;

    protected Integer total = 0;

    protected Integer records = 0;

    private String id;

    protected String oper;

    private String gridOperationMessage;

    private List reportTypeList;

    public List getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList( List reportTypeList ) {
        this.reportTypeList = reportTypeList;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords( Integer records ) {
        this.records = records;
    }

    public String getOper() {
        return oper;
    }

    public void setOper( String oper ) {
        this.oper = oper;
    }

    public String getGridOperationMessage() {
        return gridOperationMessage;
    }

    public void setGridOperationMessage( String gridOperationMessage ) {
        this.gridOperationMessage = gridOperationMessage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
    }

    public PagerFactory getPagerFactory() {
        return pagerFactory;
    }

    public void setPagerFactory( PagerFactory pagerFactory ) {
        this.pagerFactory = pagerFactory;
    }

    public void setReportManager( ReportManager reportManager ) {
        this.reportManager = reportManager;
    }

    public List getReports() {
        return reports;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String reportId = getRequest().getParameter("report.reportId");
             if (reportId != null && !reportId.equals("")) {
                 report = reportManager.get(new Long(reportId));
             }
         }*/
        this.reportTypeList = this.getDictionaryItemManager().getAllItemsByCode( "reportType" );
        this.clearSessionMessages();
    }

    /*   public String list() {
           reports = reportManager.search(query, Report.class);
           return SUCCESS;
       }*/

    public void setReportId( Long reportId ) {
        this.reportId = reportId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport( Report report ) {
        this.report = report;
    }

    public String delete() {
        reportManager.remove( report.getReportId() );
        saveMessage( getText( "report.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( reportId != null ) {
            report = reportManager.get( reportId );
            this.setEntityIsNew( false );
        }
        else {
            report = new Report();
            this.setEntityIsNew( true );
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

        boolean isNew = ( report.getReportId() == null );

        reportManager.save( report );

        String key = ( this.isEntityIsNew() ) ? "report.added" : "report.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String reportGridList() {
        try {
        	List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            
            pagedRequests = reportManager.getReportCriteria( pagedRequests,filters );
            this.reports = (List<Report>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "reportGridList Error", e );
        }
        return SUCCESS;
    }

    public String reportGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    reportManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "report.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                reportManager.save( report );
                String key = ( oper.equals( "add" ) ) ? "report.added" : "report.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( report == null ) {
            return "Invalid report Data";
        }

        return SUCCESS;

    }
}