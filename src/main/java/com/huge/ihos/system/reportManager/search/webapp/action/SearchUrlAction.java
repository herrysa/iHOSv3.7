package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.ihos.system.reportManager.search.service.SearchUrlManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class SearchUrlAction
    extends JqGridBaseAction
     {
    private SearchUrlManager searchUrlManager;

    private List searchUrls;

    private SearchUrl searchUrl;

    private String searchUrlId;

    public void setSearchUrlManager( SearchUrlManager searchUrlManager ) {
        this.searchUrlManager = searchUrlManager;
    }

    public List getSearchUrls() {
        return searchUrls;
    }

    /**
     * Grab the entity from the database before populating with request
     * parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /*
         * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
         * failures on new String searchUrlId =
         * getRequest().getParameter("searchUrl.searchUrlId"); if (searchUrlId
         * != null && !searchUrlId.equals("")) { searchUrl =
         * searchUrlManager.get(new Long(searchUrlId)); } }
         */
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

    /*
     * public String list() { searchUrls = searchUrlManager.search(query,
     * SearchUrl.class); return SUCCESS; }
     */

    public void setSearchUrlId( String searchUrlId ) {
        this.searchUrlId = searchUrlId;
    }

    public SearchUrl getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl( SearchUrl searchUrl ) {
        this.searchUrl = searchUrl;
    }

    public String delete() {
        searchUrlManager.remove( searchUrl.getSearchUrlId() );
        saveMessage( getText( "searchUrl.deleted" ) );

        return "edit_success";
    }

    private int editType = 0; // 0:new,1:edit

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
        if ( searchUrlId != null ) {
            searchUrl = searchUrlManager.get( searchUrlId );
            this.editType = 1;
        }
        else {
            searchUrl = new SearchUrl();

            Search search = this.searchManager.get( this.searchId );
            searchUrl.setSearch( search );
            searchUrl.setSearchUrlId( search.getSearchId() );
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

        boolean isNew = ( searchUrl.getSearchUrlId() == null );

        searchUrlManager.save( searchUrl );

        String key = ( isNew ) ? "searchUrl.added" : "searchUrl.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String searchUrlGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = searchUrlManager.getSearchUrlCriteria( pagedRequests, searchId );
            this.searchUrls = (List<SearchUrl>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "searchUrlGridList Error", e );
        }
        return SUCCESS;
    }

    public String searchUrlGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchUrlManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "searchUrl.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                searchUrlManager.save( searchUrl );
                String key = ( oper.equals( "add" ) ) ? "searchUrl.added" : "searchUrl.updated";
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
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */
    private String isValid() {
        if ( searchUrl == null ) {
            return "Invalid searchUrl Data";
        }

        return SUCCESS;

    }
}
