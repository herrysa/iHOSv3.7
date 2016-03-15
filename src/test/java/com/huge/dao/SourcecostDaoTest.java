package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.SourcecostDao;
import com.huge.ihos.inout.model.Sourcecost;

public class SourcecostDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private SourcecostDao sourcecostDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveSourcecost() {
        Sourcecost sourcecost = new Sourcecost();

        // enter all required fields
        sourcecost.setCheckPeriod( "OpBqLs" );
        sourcecost.setIfallot( Boolean.FALSE );

        log.debug( "adding sourcecost..." );
        sourcecost = sourcecostDao.save( sourcecost );

        sourcecost = sourcecostDao.get( sourcecost.getSourceCostId() );

        assertNotNull( sourcecost.getSourceCostId() );

        log.debug( "removing sourcecost..." );

        sourcecostDao.remove( sourcecost.getSourceCostId() );

        // should throw DataAccessException 
        sourcecostDao.get( sourcecost.getSourceCostId() );
    }
}