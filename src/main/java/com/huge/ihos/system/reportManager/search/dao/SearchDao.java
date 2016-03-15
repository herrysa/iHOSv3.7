package com.huge.ihos.system.reportManager.search.dao;

import java.sql.SQLException;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Search table.
 */
public interface SearchDao
    extends GenericDao<Search, String> {

    public Search getBySearchName( String searchName );

    public JQueryPager getSearchCriteria( final JQueryPager paginatedList );

    public String commandScript( String str )
        throws SQLException;

}