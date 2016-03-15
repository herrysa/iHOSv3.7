package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.service.impl.DataCollectionTaskManagerImpl;

public class DataCollectionTaskManagerImplTest
    extends BaseManagerMockTestCase {
    private DataCollectionTaskManagerImpl manager = null;

    private DataCollectionTaskDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataCollectionTaskDao.class );
        manager = new DataCollectionTaskManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataCollectionTask() {
        log.debug( "testing get..." );

        final Long dataCollectionTaskId = 7L;
        final DataCollectionTask dataCollectionTask = new DataCollectionTask();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( dataCollectionTaskId ) ) );
                will( returnValue( dataCollectionTask ) );
            }
        } );

        DataCollectionTask result = manager.get( dataCollectionTaskId );
        assertSame( dataCollectionTask, result );
    }

    @Test
    public void testGetDataCollectionTasks() {
        log.debug( "testing getAll..." );

        final List dataCollectionTasks = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataCollectionTasks ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataCollectionTasks, result );
    }

    @Test
    public void testSaveDataCollectionTask() {
        log.debug( "testing save..." );

        final DataCollectionTask dataCollectionTask = new DataCollectionTask();
        // enter all required fields
        dataCollectionTask.setStatus( "QzYbFtWnNmAcJyEcVgQd" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataCollectionTask ) ) );
            }
        } );

        manager.save( dataCollectionTask );
    }

    @Test
    public void testRemoveDataCollectionTask() {
        log.debug( "testing remove..." );

        final Long dataCollectionTaskId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( dataCollectionTaskId ) ) );
            }
        } );

        manager.remove( dataCollectionTaskId );
    }
}