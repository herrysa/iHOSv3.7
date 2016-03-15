package com.huge.ihos.formula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.ihos.formula.service.AllotPrincipleManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class AllotPrincipleAction
    extends JqGridBaseAction
{
    private AllotPrincipleManager allotPrincipleManager;

    private List allotPrinciples;

    private AllotPrinciple allotPrinciple;

    private String allotPrincipleId;

    public void setAllotPrincipleManager( AllotPrincipleManager allotPrincipleManager ) {
        this.allotPrincipleManager = allotPrincipleManager;
    }

    public List getAllotPrinciples() {
        return allotPrinciples;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String allotPrincipleId = getRequest().getParameter("allotPrinciple.allotPrincipleId");
             if (allotPrincipleId != null && !allotPrincipleId.equals("")) {
                 allotPrinciple = allotPrincipleManager.get(new String(allotPrincipleId));
             }
         }*/
        this.clearSessionMessages();
    }

    /*   public String list() {
           allotPrinciples = allotPrincipleManager.search(query, AllotPrinciple.class);
           return SUCCESS;
       }*/

    public void setAllotPrincipleId( String allotPrincipleId ) {
        this.allotPrincipleId = allotPrincipleId;
    }

    public AllotPrinciple getAllotPrinciple() {
        return allotPrinciple;
    }

    public void setAllotPrinciple( AllotPrinciple allotPrinciple ) {
        this.allotPrinciple = allotPrinciple;
    }

    public String delete() {
        allotPrincipleManager.remove( allotPrinciple.getAllotPrincipleId() );
        saveMessage( getText( "allotPrinciple.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( allotPrincipleId != null ) {
            allotPrinciple = allotPrincipleManager.get( allotPrincipleId );
        }
        else {
            allotPrinciple = new AllotPrinciple();
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

        boolean isNew = ( allotPrinciple.getAllotPrincipleId() == null );

        allotPrincipleManager.save( allotPrinciple );

        String key = ( isNew ) ? "allotPrinciple.added" : "allotPrinciple.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String allotPrincipleGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = allotPrincipleManager.getAllotPrincipleCriteria( pagedRequests );
            this.allotPrinciples = (List<AllotPrinciple>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "allotPrincipleGridList Error", e );
        }
        return SUCCESS;
    }

    public String allotPrincipleGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    //Long removeId = Long.parseLong(ids.nextToken());
                    String removeId = ids.nextToken();
                    allotPrincipleManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "allotPrinciple.deleted" );
                return ajaxForward(true, gridOperationMessage, false);
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                allotPrincipleManager.save( allotPrinciple );
                String key = ( oper.equals( "add" ) ) ? "allotPrinciple.added" : "allotPrinciple.updated";
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
        if ( allotPrinciple == null ) {
            return "Invalid allotPrinciple Data";
        }

        return SUCCESS;

    }
}