package com.huge.ihos.maptables.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.datacollection.maptables.dao.MatetypeDao;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;

public class MatetypeDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private MatetypeDao matetypeDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveMatetype() {
        Matetype matetype = new Matetype();

        // enter all required fields
        matetype.setMateType( "OyCsIhZxQjJsJmKlDfUxAlWdPhKpTpVcObGpRlWkLeJxEcKyLpYvFaKyXzPp" );

        log.debug( "adding matetype..." );
        matetype = matetypeDao.save( matetype );

        matetype = matetypeDao.get( matetype.getMateMapId() );

        assertNotNull( matetype.getMateMapId() );

        log.debug( "removing matetype..." );

        matetypeDao.remove( matetype.getMateMapId() );

        // should throw DataAccessException 
        matetypeDao.get( matetype.getMateMapId() );
    }
}