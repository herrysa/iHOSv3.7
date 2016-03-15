package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the SearchOption table.
 */
public interface SearchOptionDao
    extends GenericDao<SearchOption, String> {

    public SearchOption[] getSearchOptionsBySearchIdOrdered( String searchId );
    
    public SearchOption[] getFormedSearchOptions( String searchId );

    public SearchOption[] getSearchSumOptionsBySearchIdOrdered( String searchId );

    public SearchOption[] getSearchEditOptionsBySearchIdOrdered( String searchId );

    public SearchOption[] getSearchAllOptionsBySearchIdOrdered( String searchId );

    public JQueryPager getSearchOptionCriteria( final JQueryPager paginatedList, String searchId );
}