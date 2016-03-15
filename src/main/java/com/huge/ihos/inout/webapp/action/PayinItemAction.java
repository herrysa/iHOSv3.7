package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.PayinItem;
import com.huge.ihos.inout.service.PayinItemManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class PayinItemAction
    extends JqGridBaseAction
     {
    private PayinItemManager payinItemManager;

    private List payinItems;

    private PayinItem payinItem;

    private String payinItemId;

    private List payinItemSelectList;

    public List getPayinItemSelectList() {
        return payinItemSelectList;
    }

    public void setPayinItemSelectList( List payinItemSelectList ) {
        this.payinItemSelectList = payinItemSelectList;
    }

    public void setPayinItemManager( PayinItemManager payinItemManager ) {
        this.payinItemManager = payinItemManager;
    }

    public List getPayinItems() {
        return payinItems;
    }



    /*   public String list() {
           payinItems = payinItemManager.search(query, PayinItem.class);
           return SUCCESS;
       }*/

    public void setPayinItemId( String payinItemId ) {
        this.payinItemId = payinItemId;
    }

    public PayinItem getPayinItem() {
        return payinItem;
    }

    public void setPayinItem( PayinItem payinItem ) {
        this.payinItem = payinItem;
    }

    public String delete() {
        payinItemManager.remove( payinItem.getPayinItemId() );
        saveMessage( getText( "payinItem.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( payinItemId != null ) {
            payinItem = payinItemManager.get( payinItemId );
            this.setEntityIsNew( false );
        }
        else {
            payinItem = new PayinItem();
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

        boolean isNew = ( payinItem.getPayinItemId() == null );

        payinItemManager.save( payinItem );

        String key = ( this.isEntityIsNew() ) ? "payinItem.added" : "payinItem.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String payinItemGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = payinItemManager.getPayinItemCriteria( pagedRequests );
            this.payinItems = (List<PayinItem>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "payinItemGridList Error", e );
        }
        return SUCCESS;
    }

    public String payinItemGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    payinItemManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "payinItem.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                payinItemManager.save( payinItem );
                String key = ( oper.equals( "add" ) ) ? "payinItem.added" : "payinItem.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            gridOperationMessage = e.getMessage();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( payinItem == null ) {
            return "Invalid payinItem Data";
        }

        return SUCCESS;

    }

    public String allPayinItemListSelect() {

        this.payinItemSelectList = this.payinItemManager.getAll();

        return SUCCESS;
    }

}