package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.service.SearchItemManager;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class SearchItemAction
    extends JqGridBaseAction
     {
    private SearchItemManager searchItemManager;

    private List searchItems;

    private SearchItem searchItem;

    private String searchItemId;

    private String searchId;

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId( String searchId ) {
        this.searchId = searchId;
    }

    private SearchManager searchManager;

    public void setSearchManager( SearchManager searchManager ) {
        this.searchManager = searchManager;
    }

    public void setSearchItemManager( SearchItemManager searchItemManager ) {
        this.searchItemManager = searchItemManager;
    }

    public List getSearchItems() {
        return searchItems;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String searchItemId = getRequest().getParameter("searchItem.searchItemId");
             if (searchItemId != null && !searchItemId.equals("")) {
                 searchItem = searchItemManager.get(new Long(searchItemId));
             }
         }*/
        String sId = getRequest().getParameter( "searchId" );
        if ( sId != null && !sId.trim().equals( "" ) )
            this.searchId = sId;
        this.clearSessionMessages();
    }

    /*   public String list() {
           searchItems = searchItemManager.search(query, SearchItem.class);
           return SUCCESS;
       }*/

    public void setSearchItemId( String searchItemId ) {
        this.searchItemId = searchItemId;
    }

    public SearchItem getSearchItem() {
        return searchItem;
    }

    public void setSearchItem( SearchItem searchItem ) {
        this.searchItem = searchItem;
    }

    public String delete() {
        searchItemManager.remove( searchItem.getSearchItemId() );
        saveMessage( getText( "searchItem.deleted" ) );

        return "edit_success";
    }

    private int editType = 0;//0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String edit() {
        if ( searchItemId != null ) {
            searchItem = searchItemManager.get( searchItemId );
            this.editType = 1;
        }
        else {
            Search search = this.searchManager.get( this.searchId );
            searchItem = new SearchItem();
            searchItem.setSearch( search );
            searchItem.setSearchItemId( search.getSearchId() );
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

        boolean isNew = ( searchItem.getSearchItemId() == null );

        searchItemManager.save( searchItem );

        String key = ( isNew ) ? "searchItem.added" : "searchItem.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String searchItemGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = searchItemManager.getSearchItemCriteria( pagedRequests, searchId );
            this.searchItems = (List<SearchItem>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "searchItemGridList Error", e );
        }
        return SUCCESS;
    }

    public String searchItemGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchItemManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "searchItem.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                searchItemManager.save( searchItem );
                String key = ( oper.equals( "add" ) ) ? "searchItem.added" : "searchItem.updated";
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
        if ( searchItem == null ) {
            return "Invalid searchItem Data";
        }

        return SUCCESS;

    }
}