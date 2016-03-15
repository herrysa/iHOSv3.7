package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataSourceDefineDao;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.ihos.system.datacollection.service.impl.DataSourceDefineManagerImpl;

public class DataSourceDefineManagerImplTest
    extends BaseManagerMockTestCase {
    private DataSourceDefineManagerImpl manager = null;

    private DataSourceDefineDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataSourceDefineDao.class );
        manager = new DataSourceDefineManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataSourceDefine() {
        log.debug( "testing get..." );

        final Long dataSourceDefineId = 7L;
        final DataSourceDefine dataSourceDefine = new DataSourceDefine();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( dataSourceDefineId ) ) );
                will( returnValue( dataSourceDefine ) );
            }
        } );

        DataSourceDefine result = manager.get( dataSourceDefineId );
        assertSame( dataSourceDefine, result );
    }

    @Test
    public void testGetDataSourceDefines() {
        log.debug( "testing getAll..." );

        final List dataSourceDefines = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataSourceDefines ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataSourceDefines, result );
    }

    @Test
    public void testSaveDataSourceDefine() {
        log.debug( "testing save..." );

        final DataSourceDefine dataSourceDefine = new DataSourceDefine();
        // enter all required fields
        dataSourceDefine.setDataSourceName( "ZqNlFcEjIwNrGpJhZrDnIxTtAhJqLaWlZgZrIrGhViOjJeGtQh" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataSourceDefine ) ) );
            }
        } );

        manager.save( dataSourceDefine );
    }

    @Test
    public void testRemoveDataSourceDefine() {
        log.debug( "testing remove..." );

        final Long dataSourceDefineId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( dataSourceDefineId ) ) );
            }
        } );

        manager.remove( dataSourceDefineId );
    }
}