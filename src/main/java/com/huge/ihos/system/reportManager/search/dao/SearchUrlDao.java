package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the SearchUrl table.
 */
public interface SearchUrlDao
    extends GenericDao<SearchUrl, String> {

    public SearchUrl[] getSearchUrlsBySearchIdOrdered( String searchName );

    public JQueryPager getSearchUrlCriteria( final JQueryPager paginatedList, String searchId );
    
    public SearchUrl[] getSearchUrlByRight(String userId,String searchId);
}