package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the SearchItem table.
 */
public interface SearchItemDao
    extends GenericDao<SearchItem, String> {

    public SearchItem[] getEnabledSearchItemsBySearchIdOrdered( String searchId );

    public JQueryPager getSearchItemCriteria( final JQueryPager paginatedList, String parentId );
}