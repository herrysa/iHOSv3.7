package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.ChargeType;
import com.huge.ihos.inout.service.ChargeTypeManager;
import com.huge.ihos.inout.service.PayinItemManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class ChargeTypeAction
    extends JqGridBaseAction
  {

    private final String DIC_MEDORLEE_KEY = "medorlee";

    private ChargeTypeManager chargeTypeManager;

    private PayinItemManager payinItemManager;

    private List chargeTypes;

    private ChargeType chargeType;

    private String chargeTypeId;

    private List payinItemList;

    private List allChargeTypeList;

    private List dictionaryItemsSelectList;

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        this.dictionaryItemsSelectList = this.getDictionaryItemManager().getAllItemsByCode( DIC_MEDORLEE_KEY );
        this.payinItemList = this.payinItemManager.getAllPayItems();
        this.clearSessionMessages();
    }

    public String delete() {
        chargeTypeManager.remove( chargeType.getChargeTypeId() );
        saveMessage( getText( "chargeType.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( chargeTypeId != null ) {
            chargeType = chargeTypeManager.get( chargeTypeId );
            this.setEntityIsNew( false );
        }
        else {
            chargeType = new ChargeType();
            this.setEntityIsNew( true );
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        try {
            if ( cancel != null ) {
                return "cancel";
            }

            if ( delete != null ) {
                return delete();
            }

            boolean isNew = ( chargeType.getChargeTypeId() == null );

            if ( chargeType.getParent().getChargeTypeId() == null || chargeType.getParent().getChargeTypeId().trim().equalsIgnoreCase( "" ) )
                chargeType.setParent( null );
            if ( chargeType.getPayinItem().getPayinItemId() == null || chargeType.getPayinItem().getPayinItemId().trim().equalsIgnoreCase( "" ) )
                chargeType.setPayinItem( null );

            chargeTypeManager.save( chargeType );

            String key = ( this.isEntityIsNew() ) ? "chargeType.added" : "chargeType.updated";
            saveMessage( getText( key ) );
            return ajaxForward( getText( key ) );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
            return ajaxForward( "asdfasdfy" );
        }
    }

    public String chargeTypeGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            //pagedRequests = chargeTypeManager.getChargeTypeCriteria(pagedRequests);
            pagedRequests = chargeTypeManager.getAppManagerCriteriaWithSearch( pagedRequests, ChargeType.class, filters );
            this.chargeTypes = (List<ChargeType>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "chargeTypeGridList Error", e );
        }
        return SUCCESS;
    }

    public String chargeTypeGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    chargeTypeManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "chargeType.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                chargeTypeManager.save( chargeType );
                String key = ( oper.equals( "add" ) ) ? "chargeType.added" : "chargeType.updated";
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
        if ( chargeType == null ) {
            return "Invalid chargeType Data";
        }

        return SUCCESS;

    }

    public String allChargeTypeSelectList() {
        this.allChargeTypeList = this.chargeTypeManager.getAllChargeTypeList();
        return SUCCESS;
    }

    public List getDictionaryItemsSelectList() {
        return dictionaryItemsSelectList;
    }

    public List getPayinItemList() {
        return payinItemList;
    }

    public void setPayinItemList( List payinItemList ) {
        this.payinItemList = payinItemList;
    }

    public PayinItemManager getPayinItemManager() {
        return payinItemManager;
    }

    public void setPayinItemManager( PayinItemManager payinItemManager ) {
        this.payinItemManager = payinItemManager;
    }

    public void setDictionaryItemsSelectList( List dictionaryItemsSelectList ) {
        this.dictionaryItemsSelectList = dictionaryItemsSelectList;
    }

    public List getAllChargeTypeList() {
        return allChargeTypeList;
    }

    public void setAllChargeTypeList( List allChargeTypeList ) {
        this.allChargeTypeList = allChargeTypeList;
    }

    public void setChargeTypeManager( ChargeTypeManager chargeTypeManager ) {
        this.chargeTypeManager = chargeTypeManager;
    }

    public List getChargeTypes() {
        return chargeTypes;
    }

    public void setChargeTypeId( String chargeTypeId ) {
        this.chargeTypeId = chargeTypeId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType( ChargeType chargeType ) {
        this.chargeType = chargeType;
    }
}