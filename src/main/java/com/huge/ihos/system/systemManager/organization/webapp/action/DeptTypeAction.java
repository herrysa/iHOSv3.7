package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DeptTypeAction
    extends JqGridBaseAction
     {
    private DeptTypeManager deptTypeManager;

    private List deptTypes;

    private DeptType deptType;

    private String deptTypeId;

    public void setDeptTypeManager( DeptTypeManager deptTypeManager ) {
        this.deptTypeManager = deptTypeManager;
    }

    public List getDeptTypes() {
        return deptTypes;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */


    /*   public String list() {
           deptTypes = deptTypeManager.search(query, DeptType.class);
           return SUCCESS;
       }*/

    public void setDeptTypeId( String deptTypeId ) {
        this.deptTypeId = deptTypeId;
    }

    public DeptType getDeptType() {
        return deptType;
    }

    public void setDeptType( DeptType deptType ) {
        this.deptType = deptType;
    }

    public String delete() {
        deptTypeManager.remove( deptType.getDeptTypeId() );
        saveMessage( getText( "deptType.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( deptTypeId != null ) {
            deptType = deptTypeManager.get( deptTypeId );
            this.setEntityIsNew( false );
        }
        else {
            deptType = new DeptType();
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

        boolean isNew = ( deptType.getDeptTypeId() == null );

        deptTypeManager.save( deptType );

        String key = ( this.isEntityIsNew() ) ? "deptType.added" : "deptType.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String deptTypeGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = deptTypeManager.getDeptTypeCriteria( pagedRequests );
            this.deptTypes = (List<DeptType>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "deptTypeGridList Error", e );
        }
        return SUCCESS;
    }

    public String deptTypeGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    deptTypeManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "deptType.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                deptTypeManager.save( deptType );
                String key = ( oper.equals( "add" ) ) ? "deptType.added" : "deptType.updated";
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
        if ( deptType == null ) {
            return "Invalid deptType Data";
        }

        return SUCCESS;

    }

    private List deptTypeSelectList;

    public List getDeptTypeSelectList() {
        return deptTypeSelectList;
    }

    public void setDeptTypeSelectList( List deptTypeSelectList ) {
        this.deptTypeSelectList = deptTypeSelectList;
    }

    public String deptTypeAllList() {
        this.deptTypeSelectList = this.deptTypeManager.getAll();
        return this.SUCCESS;
    }
}