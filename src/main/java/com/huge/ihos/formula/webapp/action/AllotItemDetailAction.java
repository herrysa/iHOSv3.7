package com.huge.ihos.formula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.AllotItem;
import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.ihos.formula.service.AllotItemDetailManager;
import com.huge.ihos.formula.service.AllotItemManager;
import com.huge.ihos.formula.service.AllotPrincipleManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class AllotItemDetailAction
    extends JqGridBaseAction
    {
    private AllotItemDetailManager allotItemDetailManager;

    private AllotItemManager allotItemManager;

    private AllotPrincipleManager allotPrincipleManager;

    private List allotPrinciples;

    private List allotItemDetails;

    private AllotItemDetail allotItemDetail;

    private Long allotItemDetailId;

    private Long allotItemId;

    public AllotPrincipleManager getAllotPrincipleManager() {
        return allotPrincipleManager;
    }

    public void setAllotPrincipleManager( AllotPrincipleManager allotPrincipleManager ) {
        this.allotPrincipleManager = allotPrincipleManager;
    }

    public List getAllotPrinciples() {
        return allotPrinciples;
    }

    public void setAllotPrinciples( List allotPrinciples ) {
        this.allotPrinciples = allotPrinciples;
    }

    public AllotItemManager getAllotItemManager() {
        return allotItemManager;
    }

    public void setAllotItemManager( AllotItemManager allotItemManager ) {
        this.allotItemManager = allotItemManager;
    }

    public Long getAllotItemId() {
        return allotItemId;
    }

    public void setAllotItemId( Long allotItemId ) {
        this.allotItemId = allotItemId;
    }

    public void setAllotItemDetailManager( AllotItemDetailManager allotItemDetailManager ) {
        this.allotItemDetailManager = allotItemDetailManager;
    }

    public List getAllotItemDetails() {
        return allotItemDetails;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare(); 
        /*
         * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
         * failures on new String allotItemDetailId =
         * getRequest().getParameter("allotItemDetail.allotItemDetailId"); if
         * (allotItemDetailId != null && !allotItemDetailId.equals("")) {
         * allotItemDetail = allotItemDetailManager.get(new
         * Long(allotItemDetailId)); } }
         */
        /*		String sId = getRequest().getParameter("allotItemId");
         if (sId != null && !sId.trim().equals(""))
         this.allotItemId = Long.parseLong(sId);*/
        this.allotPrinciples = this.allotPrincipleManager.getAll();
        this.clearSessionMessages();
    }

    /*   public String list() {
           allotItemDetails = allotItemDetailManager.search(query, AllotItemDetail.class);
           return SUCCESS;
       }*/

    public void setAllotItemDetailId( Long allotItemDetailId ) {
        this.allotItemDetailId = allotItemDetailId;
    }

    public AllotItemDetail getAllotItemDetail() {
        return allotItemDetail;
    }

    public void setAllotItemDetail( AllotItemDetail allotItemDetail ) {
        this.allotItemDetail = allotItemDetail;
    }

    public String delete() {
        allotItemDetailManager.remove( allotItemDetail.getAllotItemDetailId() );
        saveMessage( getText( "allotItemDetail.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( allotItemDetailId != null ) {
            allotItemDetail = allotItemDetailManager.get( allotItemDetailId );
        }
        else {
            AllotItem ai = this.getAllotItemManager().get( this.allotItemId );

            allotItemDetail = new AllotItemDetail();
            allotItemDetail.setAllotItem( ai );
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

        boolean isNew = ( allotItemDetail.getAllotItemDetailId() == null );

        allotItemDetailManager.save( allotItemDetail );

        String key = ( isNew ) ? "allotItemDetail.added" : "allotItemDetail.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String allotItemDetailGridList() {
        try {
            //	HttpServletRequest req = this.getRequest();

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = allotItemDetailManager.getAllotItemDetailCriteria( pagedRequests, this.allotItemId );
            this.allotItemDetails = (List<AllotItemDetail>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "allotItemDetailGridList Error", e );
        }
        return SUCCESS;
    }

    public String allotItemDetailGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    allotItemDetailManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "allotItemDetail.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                allotItemDetailManager.save( allotItemDetail );
                String key = ( oper.equals( "add" ) ) ? "allotItemDetail.added" : "allotItemDetail.updated";
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
        if ( allotItemDetail == null ) {
            return "Invalid allotItemDetail Data";
        }

        return SUCCESS;

    }
}