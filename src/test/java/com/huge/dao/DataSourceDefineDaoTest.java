package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.datacollection.dao.DataSourceDefineDao;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;

public class DataSourceDefineDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataSourceDefineDao dataSourceDefineDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataSourceDefine() {
        DataSourceDefine dataSourceDefine = new DataSourceDefine();

        // enter all required fields
        dataSourceDefine.setDataSourceName( "BpJvTtAaInNpClBtQzAmLhBdElRpXqTuZnBfVfOgQoLqUnQyNg" );

        log.debug( "adding dataSourceDefine..." );
        dataSourceDefine = dataSourceDefineDao.save( dataSourceDefine );

        dataSourceDefine = dataSourceDefineDao.get( dataSourceDefine.getDataSourceDefineId() );

        assertNotNull( dataSourceDefine.getDataSourceDefineId() );

        log.debug( "removing dataSourceDefine..." );

        dataSourceDefineDao.remove( dataSourceDefine.getDataSourceDefineId() );

        // should throw DataAccessException 
        dataSourceDefineDao.get( dataSourceDefine.getDataSourceDefineId() );
    }
}