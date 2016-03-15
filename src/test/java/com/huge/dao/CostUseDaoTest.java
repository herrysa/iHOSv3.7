package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.CostUseDao;
import com.huge.ihos.inout.model.CostUse;

public class CostUseDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private CostUseDao costUseDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveCostUse() {
        CostUse costUse = new CostUse();

        // enter all required fields
        costUse.setCostUseName( "AxJzRcRhBdSxTwMwGpPa" );
        costUse.setDisabled( Boolean.FALSE );

        log.debug( "adding costUse..." );
        costUse = costUseDao.save( costUse );

        costUse = costUseDao.get( costUse.getCostUseId() );

        assertNotNull( costUse.getCostUseId() );

        log.debug( "removing costUse..." );

        costUseDao.remove( costUse.getCostUseId() );

        // should throw DataAccessException 
        costUseDao.get( costUse.getCostUseId() );
    }
}