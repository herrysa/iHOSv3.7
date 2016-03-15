package com.huge.ihos.system.reportManager.search.service;

import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchEntityClusterManager
    extends GenericManager<SearchEntityCluster, Long> {
    public JQueryPager getSearchEntityClusterCriteria( final JQueryPager paginatedList, Long parentId );
}
