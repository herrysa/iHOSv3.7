package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.datacollection.dao.DataSourceTypeDao;
import com.huge.ihos.system.datacollection.model.DataSourceType;

public class DataSourceTypeDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataSourceTypeDao dataSourceTypeDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataSourceType() {
        DataSourceType dataSourceType = new DataSourceType();

        // enter all required fields
        dataSourceType.setDataSourceTypeName( "UaOlPxClHzEfSiIySzXsGaSlXqGpIvGtOvGmXeDfUwSqClTgAd" );
        dataSourceType.setIsNeedFile( Boolean.FALSE );

        log.debug( "adding dataSourceType..." );
        dataSourceType = dataSourceTypeDao.save( dataSourceType );

        dataSourceType = dataSourceTypeDao.get( dataSourceType.getDataSourceTypeId() );

        assertNotNull( dataSourceType.getDataSourceTypeId() );

        log.debug( "removing dataSourceType..." );

        dataSourceTypeDao.remove( dataSourceType.getDataSourceTypeId() );

        // should throw DataAccessException 
        dataSourceTypeDao.get( dataSourceType.getDataSourceTypeId() );
    }
}