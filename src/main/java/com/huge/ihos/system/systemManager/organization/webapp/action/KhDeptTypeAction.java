package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class KhDeptTypeAction
    extends JqGridBaseAction
      {
    private KhDeptTypeManager khDeptTypeManager;
    private DepartmentManager departmentManager;

    private List khDeptTypes;

    private KhDeptType khDeptType;

    private String khDeptTypeId;

   


   

    public String delete() {
        khDeptTypeManager.remove( khDeptType.getKhDeptTypeId() );
        saveMessage( getText( "khDeptType.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( khDeptTypeId != null ) {
            khDeptType = khDeptTypeManager.get( khDeptTypeId );
            this.setEntityIsNew( false );
        }
        else {
            khDeptType = new KhDeptType();
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

        boolean isNew = ( khDeptType.getKhDeptTypeId() == null );

        khDeptTypeManager.save( khDeptType );

        String key = ( this.isEntityIsNew() ) ? "khDeptType.added" : "khDeptType.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String khDeptTypeGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = khDeptTypeManager.getKhDeptTypeCriteria( pagedRequests );
            this.khDeptTypes = (List<KhDeptType>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "deptTypeGridList Error", e );
        }
        return SUCCESS;
    }

    public String khDeptTypeGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    KhDeptType kh= khDeptTypeManager.get(removeId);
                    List list=departmentManager.getAllDeptTypeName(kh.getKhDeptTypeName());
                    if (list.size()==0) {
                    	khDeptTypeManager.remove( removeId );
                    	gridOperationMessage = this.getText( "khDeptType.deleted" );
					}else{
						gridOperationMessage="已被引用，不能被删除！";
					}
                    
                }
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                khDeptTypeManager.save( khDeptType );
                String key = ( oper.equals( "add" ) ) ? "khDeptType.added" : "khDeptType.updated";
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
        if ( khDeptType == null ) {
            return "Invalid khDeptType Data";
        }

        return SUCCESS;

    }

    private List khDeptTypeSelectList;

    public List getKhDeptTypeSelectList() {
        return khDeptTypeSelectList;
    }

    public void setKhDeptTypeSelectList( List khDeptTypeSelectList ) {
        this.khDeptTypeSelectList = khDeptTypeSelectList;
    }

    public String khDeptTypeAllList() {
        this.khDeptTypeSelectList = this.khDeptTypeManager.getAll();
        return this.SUCCESS;
    }

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	
	 public void setKhDeptTypeManager( KhDeptTypeManager khDeptTypeManager ) {
	        this.khDeptTypeManager = khDeptTypeManager;
	    }

	    public List getKhDeptTypes() {
	        return khDeptTypes;
	    }
	    public void setKhDeptTypeId( String khDeptTypeId ) {
	        this.khDeptTypeId = khDeptTypeId;
	    }

	    public KhDeptType getKhDeptType() {
	        return khDeptType;
	    }

	    public void setKhDeptType( KhDeptType khDeptType ) {
	        this.khDeptType = khDeptType;
	    }
}