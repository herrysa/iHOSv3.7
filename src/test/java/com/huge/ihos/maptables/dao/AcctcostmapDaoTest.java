package com.huge.ihos.maptables.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.datacollection.maptables.dao.AcctcostmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;

public class AcctcostmapDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private AcctcostmapDao acctcostmapDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveAcctcostmap() {
        Acctcostmap acctcostmap = new Acctcostmap();

        // enter all required fields

        log.debug( "adding acctcostmap..." );
        acctcostmap = acctcostmapDao.save( acctcostmap );

        acctcostmap = acctcostmapDao.get( acctcostmap.getAcctMapId() );

        //  assertNotNull(acctcostmap.getAcctcode());

        log.debug( "removing acctcostmap..." );

        acctcostmapDao.remove( acctcostmap.getAcctMapId() );

        // should throw DataAccessException 
        acctcostmapDao.get( acctcostmap.getAcctMapId() );
    }
}