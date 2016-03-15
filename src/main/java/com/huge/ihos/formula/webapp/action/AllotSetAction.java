package com.huge.ihos.formula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.AllotSet;
import com.huge.ihos.formula.service.AllotSetManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class AllotSetAction
    extends JqGridBaseAction
   {
    private AllotSetManager allotSetManager;

    private List allotSets;

    private AllotSet allotSet;

    private String allotSetId;

    public void setAllotSetManager( AllotSetManager allotSetManager ) {
        this.allotSetManager = allotSetManager;
    }

    public List getAllotSets() {
        return allotSets;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare(); 
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String allotSetId = getRequest().getParameter("allotSet.allotSetId");
             if (allotSetId != null && !allotSetId.equals("")) {
                 allotSet = allotSetManager.get(new String(allotSetId));
             }
         }*/
        this.clearSessionMessages();
    }

    /*   public String list() {
           allotSets = allotSetManager.search(query, AllotSet.class);
           return SUCCESS;
       }*/

    public void setAllotSetId( String allotSetId ) {
        this.allotSetId = allotSetId;
    }

    public AllotSet getAllotSet() {
        return allotSet;
    }

    public void setAllotSet( AllotSet allotSet ) {
        this.allotSet = allotSet;
    }

    public String delete() {
        allotSetManager.remove( allotSet.getAllotSetId() );
        saveMessage( getText( "allotSet.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( allotSetId != null ) {
            allotSet = allotSetManager.get( allotSetId );
        }
        else {
            allotSet = new AllotSet();
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

        boolean isNew = ( allotSet.getAllotSetId() == null );

        allotSetManager.save( allotSet );

        String key = ( isNew ) ? "allotSet.added" : "allotSet.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String allotSetGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = allotSetManager.getAllotSetCriteria( pagedRequests );
            this.allotSets = (List<AllotSet>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "allotSetGridList Error", e );
        }
        return SUCCESS;
    }

    public String allotSetGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    //					Long removeId = Long.parseLong(ids.nextToken());
                    String removeId = ids.nextToken();
                    allotSetManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "allotSet.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                allotSetManager.save( allotSet );
                String key = ( oper.equals( "add" ) ) ? "allotSet.added" : "allotSet.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( true, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( allotSet == null ) {
            return "Invalid allotSet Data";
        }

        return SUCCESS;

    }
}