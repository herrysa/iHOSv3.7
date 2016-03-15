package com.huge.ihos.formula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.DeptSplit;
import com.huge.ihos.formula.service.DeptSplitManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class DeptSplitAction
    extends JqGridBaseAction
     {
    private DeptSplitManager deptSplitManager;

    private List deptSplits;

    private DeptSplit deptSplit;

    private Long deptSplitId;

    public void setDeptSplitManager( DeptSplitManager deptSplitManager ) {
        this.deptSplitManager = deptSplitManager;
    }

    public List getDeptSplits() {
        return deptSplits;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */


    /*   public String list() {
           deptSplits = deptSplitManager.search(query, DeptSplit.class);
           return SUCCESS;
       }*/

    public void setDeptSplitId( Long deptSplitId ) {
        this.deptSplitId = deptSplitId;
    }

    public DeptSplit getDeptSplit() {
        return deptSplit;
    }

    public void setDeptSplit( DeptSplit deptSplit ) {
        this.deptSplit = deptSplit;
    }

    public String delete() {
        deptSplitManager.remove( deptSplit.getDeptSplitId() );
        saveMessage( getText( "deptSplit.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( deptSplitId != null ) {
            deptSplit = deptSplitManager.get( deptSplitId );
        }
        else {
            deptSplit = new DeptSplit();
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

        boolean isNew = ( deptSplit.getDeptSplitId() == null );

        deptSplitManager.save( deptSplit );

        String key = ( isNew ) ? "deptSplit.added" : "deptSplit.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String deptSplitGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = deptSplitManager.getDeptSplitCriteria( pagedRequests );
            this.deptSplits = (List<DeptSplit>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "deptSplitGridList Error", e );
        }
        return SUCCESS;
    }

    public String deptSplitGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    deptSplitManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "deptSplit.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                deptSplitManager.save( deptSplit );
                String key = ( oper.equals( "add" ) ) ? "deptSplit.added" : "deptSplit.updated";
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
        if ( deptSplit == null ) {
            return "Invalid deptSplit Data";
        }

        return SUCCESS;

    }
}