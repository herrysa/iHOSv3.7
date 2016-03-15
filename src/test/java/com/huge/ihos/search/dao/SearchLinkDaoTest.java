package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.SearchLinkDao;
import com.huge.ihos.system.reportManager.search.model.SearchLink;

public class SearchLinkDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SearchLinkDao searchLinkDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSearchLink() {
        SearchLink searchLink = new SearchLink();

        // enter all required fields
        searchLink.setLink( "YgVeCrMlFmNnAyZbVlKlDjGpHdBgAfPcLbSfImEhCoFxYjQnAs" );

        log.debug( "adding searchLink..." );
        searchLink = searchLinkDao.save( searchLink );

        searchLink = searchLinkDao.get( searchLink.getSearchLinkId() );

        assertNotNull( searchLink.getSearchLinkId() );

        log.debug( "removing searchLink..." );

        searchLinkDao.remove( searchLink.getSearchLinkId() );

        // should throw DataAccessException 
        searchLinkDao.get( searchLink.getSearchLinkId() );
    }
}