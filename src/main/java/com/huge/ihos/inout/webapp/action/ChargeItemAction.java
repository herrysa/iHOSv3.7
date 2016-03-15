package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.ChargeItem;
import com.huge.ihos.inout.service.ChargeItemManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class ChargeItemAction
    extends JqGridBaseAction
  {
    private ChargeItemManager chargeItemManager;

    private List chargeItems;

    private ChargeItem chargeItem;

    private Long chargeItemNo;

    public void setChargeItemManager( ChargeItemManager chargeItemManager ) {
        this.chargeItemManager = chargeItemManager;
    }

    public List getChargeItems() {
        return chargeItems;
    }
    
    private String chargeItemId;
    

    public String getChargeItemId() {
		return chargeItemId;
	}

	public void setChargeItemId(String chargeItemId) {
		this.chargeItemId = chargeItemId;
	}

	/**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String chargeItemId = getRequest().getParameter("chargeItem.chargeItemNo");
             if (chargeItemId != null && !chargeItemId.equals("")) {
                 chargeItem = chargeItemManager.get(new Long(chargeItemId));
             }
         }*/
        this.clearSessionMessages();
    }

    /*   public String list() {
           chargeItems = chargeItemManager.search(query, ChargeItem.class);
           return SUCCESS;
       }*/

    public void setChargeItemNo( Long chargeItemNo ) {
        this.chargeItemNo = chargeItemNo;
    }

    public ChargeItem getChargeItem() {
        return chargeItem;
    }

    public void setChargeItem( ChargeItem chargeItem ) {
        this.chargeItem = chargeItem;
    }

    public String delete() {
        chargeItemManager.remove( chargeItem.getChargeItemId() );
        saveMessage( getText( "chargeItem.deleted" ) );

        return "edit_success";
    }

    public String edit() {
    	log.info("editChargeItem");
        if ( chargeItemId != null ) {
            chargeItem = chargeItemManager.get( chargeItemId );
            this.setEntityIsNew( false );
        }
        else {
            chargeItem = new ChargeItem();
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

        chargeItemManager.save( chargeItem );

        String key = ( this.isEntityIsNew() ) ? "chargeItem.added" : "chargeItem.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String chargeItemGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            //pagedRequests = chargeItemManager.getChargeItemCriteria(pagedRequests);
            pagedRequests = chargeItemManager.getAppManagerCriteriaWithSearch( pagedRequests, ChargeItem.class, filters );
            this.chargeItems = (List<ChargeItem>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "chargeItemGridList Error", e );
        }
        return SUCCESS;
    }

    public String chargeItemGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    chargeItemManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "chargeItem.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                chargeItemManager.save( chargeItem );
                String key = ( oper.equals( "add" ) ) ? "chargeItem.added" : "chargeItem.updated";
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
        if ( chargeItem == null ) {
            return "Invalid chargeItem Data";
        }

        return SUCCESS;

    }
}