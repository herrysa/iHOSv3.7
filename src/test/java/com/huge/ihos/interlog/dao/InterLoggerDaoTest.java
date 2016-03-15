package com.huge.ihos.interlog.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.InterLogger;

public class InterLoggerDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private InterLoggerDao interLoggerDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveInterLogger() {
        InterLogger interLogger = new InterLogger();

        // enter all required fields
        interLogger.setLogDateTime( new java.util.Date() );
        interLogger.setLogFrom( "JgLfCrTlWvQgXzWwKxRhSzJoHxVtHeWvWcUfCvBsBhAeDiWmFa" );
        interLogger.setLogMsg( "TmFiCeXeGmXeLqYrZfZvQfIxPjEmEuVmIaDjXhHnBcVgDeIzQe" );
        interLogger.setTaskInterId( "AaXaSpDmHkBjJtOiAvFwKpUsWfXrAkVxLtTuIlBiHxSvCuMeQg" );

        log.debug( "adding interLogger..." );
        interLogger = interLoggerDao.save( interLogger );

        interLogger = interLoggerDao.get( interLogger.getLogId() );

        assertNotNull( interLogger.getLogId() );

        log.debug( "removing interLogger..." );

        interLoggerDao.remove( interLogger.getLogId() );

        // should throw DataAccessException 
        interLoggerDao.get( interLogger.getLogId() );
    }
}