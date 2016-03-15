package com.huge.ihos.maptables.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.datacollection.maptables.dao.DeptmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Deptmap;

public class DeptmapDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DeptmapDao deptmapDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDeptmap() {
        Deptmap deptmap = new Deptmap();

        // enter all required fields
        deptmap.setMarktype( "IsQvFnEpGlDuZfYnUpTqMoYkGlZoMwZjYkRhVyEgYcKpAlTjQfOlArEvShOm" );
        deptmap.setSourcecode( "CcNeDcYlIoDeRpAsCtUmWnTpZsTxPgFwUxByGtIvCrGuBcVjMb" );
        deptmap.setSourcename( "ApWoHaUlWbNhPeRxBpSaUuClOsFjPbKwNnWyGdKkSbBxLbFnSwXxRzCzYhUlWmLlZgFhOdVgHjLiUpJtRyYwDzSbEbNnLlVeXnId" );

        log.debug( "adding deptmap..." );
        deptmap = deptmapDao.save( deptmap );

        deptmap = deptmapDao.get( deptmap.getDeptMapId() );

        assertNotNull( deptmap.getDeptMapId() );

        log.debug( "removing deptmap..." );

        deptmapDao.remove( deptmap.getDeptMapId() );

        // should throw DataAccessException 
        deptmapDao.get( deptmap.getDeptMapId() );
    }
}