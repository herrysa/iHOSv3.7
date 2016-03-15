package com.huge.ihos.system.reportManager.search.webapp.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.util.HaspDogHandler;
import com.huge.util.TestTimer;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class SearchAction
    extends JqGridBaseAction
     {
    private SearchManager searchManager;

    private List searches;

    private Search search;

    private String searchId;

    private String cmdSciptContent;

    /*private String conditionT;*/

    private List searchBarTypeList;

    private List<Menu> subSystems;
    
    private MenuManager menuManager;
    
    private Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();

	public Map<String, Boolean> getDogMenus() {
		return dogMenus;
	}

	public void setDogMenus(Map<String, Boolean> dogMenus) {
		this.dogMenus = dogMenus;
	}
    /**
     * Grab the entity from the database before populating with request parameters
     * @throws Exception 
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        this.searchBarTypeList = this.getDictionaryItemManager().getAllItemsByCode( "searchBarType" );
        this.clearSessionMessages();
        List<Menu> subsystems = this.menuManager.getAllSubSystem();
        this.subSystems = new ArrayList();
        for(Menu menu : subsystems){
        	if((dogMenus.get(menu.getMenuId())==null)?false:true){
        		this.subSystems.add(menu);
        	}
        }
    }

    public String delete() {
        searchManager.remove( search.getSearchId() );
        saveMessage( getText( "search.deleted" ) );

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
        if ( searchId != null ) {
            search = searchManager.get( searchId );
            this.editType = 1;
        }
        else {
            search = new Search();
            this.editType = 0;
        }

        return SUCCESS;
    }

    public String commandScript()
        throws SQLException {
        String huoQ = searchManager.commandScript( cmdSciptContent );
        if ( huoQ.equals( "success" ) ) {
            gridOperationMessage = this.getText( "commandScript.success" );
            return ajaxForward( true, gridOperationMessage, false );
        }
        else {
            gridOperationMessage = this.getText( "commandScript.error" );
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( search.getSearchId() == null );

        searchManager.save( search );

        String key = ( isNew ) ? "search.added" : "search.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String searchGridList() {
        try {
            TestTimer tt = new TestTimer( "searchGridList" );
            tt.begin();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );

            pagedRequests = searchManager.getAppManagerCriteriaWithSearch( pagedRequests, Search.class, filters );

            /*			JQueryPager pagedRequests = null;
             pagedRequests = (JQueryPager) pagerFactory.getPager(
             PagerFactory.JQUERYTYPE, getRequest());
             pagedRequests = searchManager.getSearchCriteria(pagedRequests);*/

            this.searches = (List<Search>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
            tt.done();
        }
        catch ( Exception e ) {
            log.error( "searchGridList Error", e );
        }
        return SUCCESS;
    }

    public String searchGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "search.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                searchManager.save( search );
                String key = ( oper.equals( "add" ) ) ? "search.added" : "search.updated";
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
        if ( search == null ) {
            return "Invalid search Data";
        }

        return SUCCESS;

    }

    public String getCmdSciptContent() {
        return cmdSciptContent;
    }

    public void setCmdSciptContent( String cmdSciptContent ) {
        this.cmdSciptContent = cmdSciptContent;
    }

    public void setSearchId( String searchId ) {
        this.searchId = searchId;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch( Search search ) {
        this.search = search;
    }

    public void setSearchManager( SearchManager searchManager ) {
        this.searchManager = searchManager;
    }

    public List getSearches() {
        return searches;
    }

    public List getSearchBarTypeList() {
        return searchBarTypeList;
    }
    public List<Menu> getSubSystems() {
		return subSystems;
	}

	public void setSubSystems(List<Menu> subSystems) {
		this.subSystems = subSystems;
	}

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

}