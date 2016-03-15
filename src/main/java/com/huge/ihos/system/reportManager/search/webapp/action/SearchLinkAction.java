package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.ihos.system.reportManager.search.service.SearchLinkManager;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class SearchLinkAction
    extends JqGridBaseAction
     {
    private SearchLinkManager searchLinkManager;

    private List searchLinks;

    private SearchLink searchLink;

    private String searchLinkId;

    public void setSearchLinkManager( SearchLinkManager searchLinkManager ) {
        this.searchLinkManager = searchLinkManager;
    }

    public List getSearchLinks() {
        return searchLinks;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String searchLinkId = getRequest().getParameter("searchLink.searchLinkId");
             if (searchLinkId != null && !searchLinkId.equals("")) {
                 searchLink = searchLinkManager.get(new Long(searchLinkId));
             }
         }*/
        String sId = getRequest().getParameter( "searchId" );
        if ( sId != null && !sId.trim().equals( "" ) )
            this.searchId = sId;
        this.clearSessionMessages();
    }

    private String searchId;

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId( String searchId ) {
        this.searchId = searchId;
    }

    public void setSearchLinkId( String searchLinkId ) {
        this.searchLinkId = searchLinkId;
    }

    public SearchLink getSearchLink() {
        return searchLink;
    }

    public void setSearchLink( SearchLink searchLink ) {
        this.searchLink = searchLink;
    }

    public String delete() {
        searchLinkManager.remove( searchLink.getSearchLinkId() );
        saveMessage( getText( "searchLink.deleted" ) );

        return "edit_success";
    }

    private int editType = 0;//0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    private SearchManager searchManager;

    public void setSearchManager( SearchManager searchManager ) {
        this.searchManager = searchManager;
    }

    public String edit() {
        if ( searchLinkId != null ) {
            searchLink = searchLinkManager.get( searchLinkId );
            this.editType = 1;
        }
        else {
            searchLink = new SearchLink();

            Search search = this.searchManager.get( this.searchId );
            searchLink.setSearch( search );
            searchLink.setSearchLinkId( search.getSearchId() );
            this.editType = 0;
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

        boolean isNew = ( searchLink.getSearchLinkId() == null );

        searchLinkManager.save( searchLink );

        String key = ( isNew ) ? "searchLink.added" : "searchLink.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String searchLinkGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = searchLinkManager.getSearchLinkCriteria( pagedRequests, searchId );
            this.searchLinks = (List<SearchLink>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "searchLinkGridList Error", e );
        }
        return SUCCESS;
    }

    public String searchLinkGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchLinkManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "searchLink.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                searchLinkManager.save( searchLink );
                String key = ( oper.equals( "add" ) ) ? "searchLink.added" : "searchLink.updated";
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
        if ( searchLink == null ) {
            return "Invalid searchLink Data";
        }

        return SUCCESS;

    }
}