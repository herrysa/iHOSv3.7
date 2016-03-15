package com.huge.ihos.system.reportManager.search.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchManager" )
public class SearchManagerImpl
    extends GenericManagerImpl<Search, String>
    implements SearchManager {
    SearchDao searchDao;

    @Autowired
    public SearchManagerImpl( SearchDao searchDao ) {
        super( searchDao );
        this.searchDao = searchDao;
    }

    /*@Cacheable("searchs")*/
    public JQueryPager getSearchCriteria( JQueryPager paginatedList ) {
        return searchDao.getSearchCriteria( paginatedList );
    }

    public String commandScript( String str )
        throws SQLException {
        return searchDao.commandScript( str );
    }

    @Override
    //@Cacheable(value="searchs",key="#id + 'getSearch'")
    public Search get( String id ) {
        return super.get( id );
    }

    @Override
    //@CacheEvict(value = "searchs", allEntries=true)
    public Search save( Search object ) {
        return super.save( object );
    }

    @Override
    //@CacheEvict(value = "searchs", allEntries=true)
    public void remove( String id ) {
        super.remove( id );
    }

}