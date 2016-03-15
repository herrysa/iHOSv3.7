package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchOptionDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.SearchOptionManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchOptionManager" )
public class SearchOptionManagerImpl
    extends GenericManagerImpl<SearchOption, String>
    implements SearchOptionManager {
    SearchOptionDao searchOptionDao;

    @Autowired
    public SearchOptionManagerImpl( SearchOptionDao searchOptionDao ) {
        super( searchOptionDao );
        this.searchOptionDao = searchOptionDao;
    }

    //@Cacheable(value="searchOptions")
    public JQueryPager getSearchOptionCriteria( JQueryPager paginatedList, String searchId ) {
        return searchOptionDao.getSearchOptionCriteria( paginatedList, searchId );
    }

    @Override
    //@CacheEvict(value = "searchOptions", allEntries=true)
    public SearchOption save( SearchOption object ) {
        return super.save( object );
    }

    @Override
    //@CacheEvict(value = "searchOptions", allEntries=true)
    public void remove( String id ) {
        super.remove( id );
    }

    @Override
    //@Cacheable(value="searchOptions",key="#id + 'getSearchOption'")
    public SearchOption get( String id ) {
        return super.get( id );
    }

}