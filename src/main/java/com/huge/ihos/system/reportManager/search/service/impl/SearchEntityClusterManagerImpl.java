package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchEntityClusterDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.ihos.system.reportManager.search.service.SearchEntityClusterManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchEntityClusterManager" )
public class SearchEntityClusterManagerImpl
    extends GenericManagerImpl<SearchEntityCluster, Long>
    implements SearchEntityClusterManager {

    SearchEntityClusterDao searchEntityClusterDao;

    @Autowired
    public SearchEntityClusterManagerImpl( SearchEntityClusterDao searchEntityClusterDao ) {
        super( searchEntityClusterDao );
        this.searchEntityClusterDao = searchEntityClusterDao;
    }

    //@Cacheable("searchItems")
    public JQueryPager getSearchEntityClusterCriteria( JQueryPager paginatedList, Long parentId ) {
        return searchEntityClusterDao.getSearchEntityClusterCriteria( paginatedList, parentId );
    }
}
