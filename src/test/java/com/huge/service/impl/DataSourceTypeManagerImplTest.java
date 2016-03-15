package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataSourceTypeDao;
import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.ihos.system.datacollection.service.impl.DataSourceTypeManagerImpl;

public class DataSourceTypeManagerImplTest
    extends BaseManagerMockTestCase {
    private DataSourceTypeManagerImpl manager = null;

    private DataSourceTypeDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataSourceTypeDao.class );
        manager = new DataSourceTypeManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataSourceType() {
        log.debug( "testing get..." );

        final Long dataSourceTypeId = 7L;
        final DataSourceType dataSourceType = new DataSourceType();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( dataSourceTypeId ) ) );
                will( returnValue( dataSourceType ) );
            }
        } );

        DataSourceType result = manager.get( dataSourceTypeId );
        assertSame( dataSourceType, result );
    }

    @Test
    public void testGetDataSourceTypes() {
        log.debug( "testing getAll..." );

        final List dataSourceTypes = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataSourceTypes ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataSourceTypes, result );
    }

    @Test
    public void testSaveDataSourceType() {
        log.debug( "testing save..." );

        final DataSourceType dataSourceType = new DataSourceType();
        // enter all required fields
        dataSourceType.setDataSourceTypeName( "EuXtGgXrOfIvWyIjKvMySvJsRiPxRjCaFxUpNrUuIwNzOqJdZe" );
        dataSourceType.setIsNeedFile( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataSourceType ) ) );
            }
        } );

        manager.save( dataSourceType );
    }

    @Test
    public void testRemoveDataSourceType() {
        log.debug( "testing remove..." );

        final Long dataSourceTypeId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( dataSourceTypeId ) ) );
            }
        } );

        manager.remove( dataSourceTypeId );
    }
}