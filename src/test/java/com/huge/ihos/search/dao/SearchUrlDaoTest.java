package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.SearchUrlDao;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;

public class SearchUrlDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SearchUrlDao searchUrlDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSearchUrl() {
        SearchUrl searchUrl = new SearchUrl();

        // enter all required fields
        searchUrl.setNullFlag( Boolean.FALSE );
        searchUrl.setTitle( "RiAeYtHnBrMmGrWgUjPiGjAlYtZdYcDzWfVgMjBqFxPpSwCxRs" );

        log.debug( "adding searchUrl..." );
        searchUrl = searchUrlDao.save( searchUrl );

        searchUrl = searchUrlDao.get( searchUrl.getSearchUrlId() );

        assertNotNull( searchUrl.getSearchUrlId() );

        log.debug( "removing searchUrl..." );

        searchUrlDao.remove( searchUrl.getSearchUrlId() );

        // should throw DataAccessException 
        searchUrlDao.get( searchUrl.getSearchUrlId() );
    }
}