package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;

public class MccKeyDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private MccKeyDao mccKeyDao;

    @Ignore
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveMccKey() {
        MccKey mccKey = new MccKey();

        // enter all required fields

        log.debug( "adding mccKey..." );
        mccKey = mccKeyDao.save( mccKey );

        mccKey = mccKeyDao.get( mccKey.getMccKeyId() );

        assertNotNull( mccKey.getMccKeyId() );

        log.debug( "removing mccKey..." );

        mccKeyDao.remove( mccKey.getMccKeyId() );

        // should throw DataAccessException 
        mccKeyDao.get( mccKey.getMccKeyId() );
    }
    @Test
    public void testCkey(){
    	System.out.println(mccKeyDao.getCkey("12312"));
    }
}