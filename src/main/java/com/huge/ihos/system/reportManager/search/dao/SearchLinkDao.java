package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the SearchLink table.
 */
public interface SearchLinkDao
    extends GenericDao<SearchLink, String> {

    public JQueryPager getSearchLinkCriteria( final JQueryPager paginatedList, String searchId );
}