package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.ihos.system.reportManager.search.service.SearchOptionManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class SearchOptionAction
    extends JqGridBaseAction
     {
    private SearchOptionManager searchOptionManager;

    private List searchOptions;

    private SearchOption searchOption;

    private String searchOptionId;

    public void setSearchOptionManager( SearchOptionManager searchOptionManager ) {
        this.searchOptionManager = searchOptionManager;
    }

    public List getSearchOptions() {
        return searchOptions;
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
         * failures on new String searchOptionId =
         * getRequest().getParameter("searchOption.searchOptionId"); if
         * (searchOptionId != null && !searchOptionId.equals("")) { searchOption
         * = searchOptionManager.get(new Long(searchOptionId)); } }
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
     * public String list() { searchOptions = searchOptionManager.search(query,
     * SearchOption.class); return SUCCESS; }
     */

    public void setSearchOptionId( String searchOptionId ) {
        this.searchOptionId = searchOptionId;
    }

    public SearchOption getSearchOption() {
        return searchOption;
    }

    public void setSearchOption( SearchOption searchOption ) {
        this.searchOption = searchOption;
    }

    public String delete() {
        searchOptionManager.remove( searchOption.getSearchOptionId() );
        saveMessage( getText( "searchOption.deleted" ) );

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
        if ( searchOptionId != null ) {
            searchOption = searchOptionManager.get( searchOptionId );
            this.editType = 1;
        }
        else {
            searchOption = new SearchOption();
            Search search = this.searchManager.get( this.searchId );
            searchOption.setSearch( search );
            searchOption.setSearchOptionId( search.getSearchId() );

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

        boolean isNew = ( searchOption.getSearchOptionId() == null );

        searchOptionManager.save( searchOption );

        String key = ( isNew ) ? "searchOption.added" : "searchOption.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String searchOptionGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = searchOptionManager.getSearchOptionCriteria( pagedRequests, searchId );
            this.searchOptions = (List<SearchOption>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "searchOptionGridList Error", e );
        }
        return SUCCESS;
    }

    public String searchOptionGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchOptionManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "searchOption.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                searchOptionManager.save( searchOption );
                String key = ( oper.equals( "add" ) ) ? "searchOption.added" : "searchOption.updated";
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
        if ( searchOption == null ) {
            return "Invalid searchOption Data";
        }

        return SUCCESS;

    }
}
