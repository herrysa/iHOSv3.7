package com.huge.ihos.system.reportManager.search.service;

import java.sql.SQLException;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchManager
    extends GenericManager<Search, String> {
    public JQueryPager getSearchCriteria( final JQueryPager paginatedList );

    public String commandScript( String str )
        throws SQLException;

}