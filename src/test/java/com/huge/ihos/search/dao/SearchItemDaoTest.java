package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.model.SearchItem;

public class SearchItemDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SearchItemDao searchItemDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSearchItem() {
        SearchItem searchItem = new SearchItem();

        // enter all required fields
        searchItem.setSearchFlag( Boolean.FALSE );

        log.debug( "adding searchItem..." );
        searchItem = searchItemDao.save( searchItem );

        searchItem = searchItemDao.get( searchItem.getSearchItemId() );

        assertNotNull( searchItem.getSearchItemId() );

        log.debug( "removing searchItem..." );

        searchItemDao.remove( searchItem.getSearchItemId() );

        // should throw DataAccessException 
        searchItemDao.get( searchItem.getSearchItemId() );
    }

   // @Test
    public void testGetSearchBySearchIdOrdered() {
        SearchItem[] so = searchItemDao.getEnabledSearchItemsBySearchIdOrdered( "10000" );
        Assert.assertTrue( so.length > 0 );
    }
}