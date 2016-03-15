package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDefineDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;

public class DataCollectionTaskDefineDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataCollectionTaskDefineDao dataCollectionTaskDefineDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataCollectionTaskDefine() {
        DataCollectionTaskDefine dataCollectionTaskDefine = new DataCollectionTaskDefine();

        // enter all required fields
        dataCollectionTaskDefine.setIsUsed( Boolean.FALSE );
        dataCollectionTaskDefine.setTargetTableName( "UeYgSxMfElKvUoHiNxVpNpQlLgGpQnHnKhKsOhAeFhJdArIlNy" );
        dataCollectionTaskDefine.setTemporaryTableName( "KuBbWfQgEtFhExHwXxSkBdSsIlQtTvHoZzXpPxXkCiNcPqLdHg" );

        log.debug( "adding dataCollectionTaskDefine..." );
        dataCollectionTaskDefine = dataCollectionTaskDefineDao.save( dataCollectionTaskDefine );

        dataCollectionTaskDefine = dataCollectionTaskDefineDao.get( dataCollectionTaskDefine.getDataCollectionTaskDefineId() );

        assertNotNull( dataCollectionTaskDefine.getDataCollectionTaskDefineId() );

        log.debug( "removing dataCollectionTaskDefine..." );

        dataCollectionTaskDefineDao.remove( dataCollectionTaskDefine.getDataCollectionTaskDefineId() );

        // should throw DataAccessException 
        dataCollectionTaskDefineDao.get( dataCollectionTaskDefine.getDataCollectionTaskDefineId() );
    }
}