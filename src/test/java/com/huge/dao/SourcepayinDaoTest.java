package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.SourcepayinDao;
import com.huge.ihos.inout.model.Sourcepayin;

public class SourcepayinDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SourcepayinDao sourcepayinDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSourcepayin() {
        Sourcepayin sourcepayin = new Sourcepayin();

        // enter all required fields
        sourcepayin.setAmount( null );
        sourcepayin.setCheckPeriod( "YkWlMl" );
        sourcepayin.setOutin( "FyTtSo" );

        log.debug( "adding sourcepayin..." );
        sourcepayin = sourcepayinDao.save( sourcepayin );

        sourcepayin = sourcepayinDao.get( sourcepayin.getSourcePayinId() );

        assertNotNull( sourcepayin.getSourcePayinId() );

        log.debug( "removing sourcepayin..." );

        sourcepayinDao.remove( sourcepayin.getSourcePayinId() );

        // should throw DataAccessException 
        sourcepayinDao.get( sourcepayin.getSourcePayinId() );
    }
}