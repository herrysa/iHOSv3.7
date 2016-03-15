package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.SearchOptionDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;

public class SearchOptionDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SearchOptionDao searchOptionDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSearchOption() {
        SearchOption searchOption = new SearchOption();

        // enter all required fields
        searchOption.setFieldName( "PhGlIcYoFqJuFyPgAqBqDpQnCqTwDv" );
        searchOption.setPrintable( Boolean.FALSE );
        searchOption.setVisible( Boolean.FALSE );

        log.debug( "adding searchOption..." );
        searchOption = searchOptionDao.save( searchOption );

        searchOption = searchOptionDao.get( searchOption.getSearchOptionId() );

        assertNotNull( searchOption.getSearchOptionId() );

        log.debug( "removing searchOption..." );

        searchOptionDao.remove( searchOption.getSearchOptionId() );

        // should throw DataAccessException 
        searchOptionDao.get( searchOption.getSearchOptionId() );
    }

   // @Test
    public void testGetSearchBySearchIdOrdered() {
        SearchOption[] so = searchOptionDao.getSearchOptionsBySearchIdOrdered( "10000" );
        Assert.assertTrue( so.length > 0 );
    }
}