package com.huge.ihos.system.reportManager.search.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchEntityClusterDao
    extends GenericDao<SearchEntityCluster, Long> {
    public JQueryPager getSearchEntityClusterCriteria( final JQueryPager paginatedList, Long parentId );
}
