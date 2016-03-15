package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.SearchEntity;
import com.huge.ihos.system.reportManager.search.service.SearchEntityManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class SearchEntityAction
    extends JqGridBaseAction
     {
    private SearchEntityManager searchEntityManager;

    private List searchEntities;

    private SearchEntity searchEntity;

    private Long entityId;

    public List getSearchEntities() {
        return searchEntities;
    }

    public void setSearchEntities( List searchEntities ) {
        this.searchEntities = searchEntities;
    }

    public SearchEntity getSearchEntity() {
        return searchEntity;
    }

    public void setSearchEntity( SearchEntity searchEntity ) {
        this.searchEntity = searchEntity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId( Long entityId ) {
        this.entityId = entityId;
    }

    public SearchEntityManager getSearchEntityManager() {
        return searchEntityManager;
    }

    public void setSearchEntityManager( SearchEntityManager searchEntityManager ) {
        this.searchEntityManager = searchEntityManager;
    }


    public String searchEntityGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = searchEntityManager.getAppManagerCriteriaWithSearch( pagedRequests, SearchEntity.class, filters );
            this.searchEntities = (List<SearchEntity>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            log.error( "searchGridList Error", e );
        }
        return SUCCESS;
    }

    private int editType = 0; // 0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String edit() {
        if ( this.entityId != null ) {
            searchEntity = searchEntityManager.get( entityId );
            this.editType = 1;
        }
        else {
            searchEntity = new SearchEntity();
            this.editType = 0;
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        boolean isNew = ( searchEntity.getEntityId() == null );
        searchEntityManager.save( searchEntity );
        String key = ( isNew ) ? "entity.added" : "entity.updated";
        saveMessage( getText( key ) );
        return ajaxForward( getText( key ) );
    }

    public String searchEntityGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    searchEntityManager.remove( Long.parseLong( removeId ) );
                }
                gridOperationMessage = this.getText( "entity.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
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
}
