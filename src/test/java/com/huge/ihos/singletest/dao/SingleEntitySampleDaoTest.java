package com.huge.ihos.singletest.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.singletest.model.SingleEntitySample;

public class SingleEntitySampleDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SingleEntitySampleDao singleEntitySampleDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSingleEntitySample() {
        SingleEntitySample singleEntitySample = new SingleEntitySample();

        // enter all required fields
        singleEntitySample.setBigDecimalField( new BigDecimal( 0 ) );
        singleEntitySample.setBooleanField( Boolean.FALSE );
        singleEntitySample.setDateField( new java.util.Date() );
        singleEntitySample.setDoubleField( new Double( 9.37020884351119E307 ) );
        singleEntitySample.setFloatField( new Float( 5.1792816E37 ) );
        singleEntitySample.setIntField( 1301037353 );
        singleEntitySample.setStringField( "ZrBvNfJvClFpCfSwDbCjCvJwXfYzHdArVhTcKdQzFrElBhHkUd" );

        log.debug( "adding singleEntitySample..." );
        singleEntitySample = singleEntitySampleDao.save( singleEntitySample );

        singleEntitySample = singleEntitySampleDao.get( singleEntitySample.getPkid() );

        assertNotNull( singleEntitySample.getPkid() );

        log.debug( "removing singleEntitySample..." );

        singleEntitySampleDao.remove( singleEntitySample.getPkid() );

        // should throw DataAccessException 
        singleEntitySampleDao.get( singleEntitySample.getPkid() );
    }
}