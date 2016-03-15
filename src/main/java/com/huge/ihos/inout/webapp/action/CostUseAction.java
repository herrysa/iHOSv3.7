package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.CostUse;
import com.huge.ihos.inout.service.CostUseManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class CostUseAction
    extends JqGridBaseAction
 {
    private CostUseManager costUseManager;

    private List costUses;

    private CostUse costUse;

    private String costUseId;

    private List allCostUseList;

    public List getAllCostUseList() {
        return allCostUseList;
    }

    public void setAllCostUseList( List allCostUseList ) {
        this.allCostUseList = allCostUseList;
    }

    public void setCostUseManager( CostUseManager costUseManager ) {
        this.costUseManager = costUseManager;
    }

    public List getCostUses() {
        return costUses;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String costUseId = getRequest().getParameter("costUse.costUseId");
             if (costUseId != null && !costUseId.equals("")) {
                 costUse = costUseManager.get(new String(costUseId));
             }
         }*/
        this.clearSessionMessages();
    }

    /*   public String list() {
           costUses = costUseManager.search(query, CostUse.class);
           return SUCCESS;
       }*/

    public void setCostUseId( String costUseId ) {
        this.costUseId = costUseId;
    }

    public CostUse getCostUse() {
        return costUse;
    }

    public void setCostUse( CostUse costUse ) {
        this.costUse = costUse;
    }

    public String delete() {
        costUseManager.remove( costUse.getCostUseId() );
        saveMessage( getText( "costUse.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( costUseId != null ) {
            costUse = costUseManager.get( costUseId );
            this.setEntityIsNew( false );
        }
        else {
            costUse = new CostUse();
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

        boolean isNew = ( costUse.getCostUseId() == null );

        costUseManager.save( costUse );

        String key = ( this.isEntityIsNew() ) ? "costUse.added" : "costUse.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String costUseGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = costUseManager.getCostUseCriteria( pagedRequests );
            this.costUses = (List<CostUse>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "costUseGridList Error", e );
        }
        return SUCCESS;
    }

    public String costUseGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();

                    costUseManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "costUse.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                costUseManager.save( costUse );
                String key = ( oper.equals( "add" ) ) ? "costUse.added" : "costUse.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            gridOperationMessage = e.getMessage();
            return ajaxForward( false, "所选记录被引用，不能删除！", false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( costUse == null ) {
            return "Invalid costUse Data";
        }

        return SUCCESS;

    }

    public String allCostUseListSelect() {
        this.allCostUseList = this.costUseManager.getAll();
        return SUCCESS;
    }
}