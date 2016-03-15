package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.service.SearchItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchItemManager" )
public class SearchItemManagerImpl
    extends GenericManagerImpl<SearchItem, String>
    implements SearchItemManager {
    SearchItemDao searchItemDao;

    @Autowired
    public SearchItemManagerImpl( SearchItemDao searchItemDao ) {
        super( searchItemDao );
        this.searchItemDao = searchItemDao;
    }

    //@Cacheable("searchItems")
    public JQueryPager getSearchItemCriteria( JQueryPager paginatedList, String parentId ) {
        return searchItemDao.getSearchItemCriteria( paginatedList, parentId );
    }

    @Override
    //@CacheEvict(value = "searchItems", allEntries=true)
    public SearchItem save( SearchItem object ) {
        return super.save( object );
    }

    @Override
    //@CacheEvict(value = "searchItems", allEntries=true)
    public void remove( String id ) {
        super.remove( id );
    }

    @Override
    //@Cacheable(value="searchItems",key="#id + 'getSearchItem'")
    public SearchItem get( String id ) {
        return super.get( id );
    }

}