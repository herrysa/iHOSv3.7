package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDefineDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.datacollection.service.impl.DataCollectionTaskDefineManagerImpl;

public class DataCollectionTaskDefineManagerImplTest
    extends BaseManagerMockTestCase {
    private DataCollectionTaskDefineManagerImpl manager = null;

    private DataCollectionTaskDefineDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataCollectionTaskDefineDao.class );
        manager = new DataCollectionTaskDefineManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataCollectionTaskDefine() {
        log.debug( "testing get..." );

        final Long dataCollectionTaskDefineId = 7L;
        final DataCollectionTaskDefine dataCollectionTaskDefine = new DataCollectionTaskDefine();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( dataCollectionTaskDefineId ) ) );
                will( returnValue( dataCollectionTaskDefine ) );
            }
        } );

        DataCollectionTaskDefine result = manager.get( dataCollectionTaskDefineId );
        assertSame( dataCollectionTaskDefine, result );
    }

    @Test
    public void testGetDataCollectionTaskDefines() {
        log.debug( "testing getAll..." );

        final List dataCollectionTaskDefines = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataCollectionTaskDefines ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataCollectionTaskDefines, result );
    }

    @Test
    public void testSaveDataCollectionTaskDefine() {
        log.debug( "testing save..." );

        final DataCollectionTaskDefine dataCollectionTaskDefine = new DataCollectionTaskDefine();
        // enter all required fields
        dataCollectionTaskDefine.setIsUsed( Boolean.FALSE );
        dataCollectionTaskDefine.setTargetTableName( "RrOaGzOcFxHuZsHpViKtBzJdZnEjPhLbUfRiVxNaChBsJuDeQu" );
        dataCollectionTaskDefine.setTemporaryTableName( "KwYySjIfMoXhJjLeRpMbRwCxJtYdGkKxSbIdIrWmBqYnShKeTq" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataCollectionTaskDefine ) ) );
            }
        } );

        manager.save( dataCollectionTaskDefine );
    }

    @Test
    public void testRemoveDataCollectionTaskDefine() {
        log.debug( "testing remove..." );

        final Long dataCollectionTaskDefineId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( dataCollectionTaskDefineId ) ) );
            }
        } );

        manager.remove( dataCollectionTaskDefineId );
    }
}