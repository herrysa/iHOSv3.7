package com.huge.ihos.datacollection.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepExecuteDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.service.impl.DataCollectionTaskStepExecuteManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class DataCollectionTaskStepExecuteManagerImplTest
    extends BaseManagerMockTestCase {
    private DataCollectionTaskStepExecuteManagerImpl manager = null;

    private DataCollectionTaskStepExecuteDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataCollectionTaskStepExecuteDao.class );
        manager = new DataCollectionTaskStepExecuteManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataCollectionTaskStepExecute() {
        log.debug( "testing get..." );

        final Long stepExecuteId = 7L;
        final DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( stepExecuteId ) ) );
                will( returnValue( dataCollectionTaskStepExecute ) );
            }
        } );

        DataCollectionTaskStepExecute result = manager.get( stepExecuteId );
        assertSame( dataCollectionTaskStepExecute, result );
    }

    @Test
    public void testGetDataCollectionTaskStepExecutes() {
        log.debug( "testing getAll..." );

        final List dataCollectionTaskStepExecutes = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataCollectionTaskStepExecutes ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataCollectionTaskStepExecutes, result );
    }

    @Test
    public void testSaveDataCollectionTaskStepExecute() {
        log.debug( "testing save..." );

        final DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();
        // enter all required fields

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataCollectionTaskStepExecute ) ) );
            }
        } );

        manager.save( dataCollectionTaskStepExecute );
    }

    @Test
    public void testRemoveDataCollectionTaskStepExecute() {
        log.debug( "testing remove..." );

        final Long stepExecuteId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( stepExecuteId ) ) );
            }
        } );

        manager.remove( stepExecuteId );
    }
}