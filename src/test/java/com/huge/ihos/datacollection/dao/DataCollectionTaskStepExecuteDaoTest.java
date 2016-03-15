package com.huge.ihos.datacollection.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepExecuteDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;

public class DataCollectionTaskStepExecuteDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataCollectionTaskStepExecuteDao dataCollectionTaskStepExecuteDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataCollectionTaskStepExecute() {
        DataCollectionTaskStepExecute dataCollectionTaskStepExecute = new DataCollectionTaskStepExecute();

        // enter all required fields

        log.debug( "adding dataCollectionTaskStepExecute..." );
        dataCollectionTaskStepExecute = dataCollectionTaskStepExecuteDao.save( dataCollectionTaskStepExecute );

        dataCollectionTaskStepExecute = dataCollectionTaskStepExecuteDao.get( dataCollectionTaskStepExecute.getStepExecuteId() );

        assertNotNull( dataCollectionTaskStepExecute.getStepExecuteId() );

        log.debug( "removing dataCollectionTaskStepExecute..." );

        dataCollectionTaskStepExecuteDao.remove( dataCollectionTaskStepExecute.getStepExecuteId() );

        // should throw DataAccessException 
        dataCollectionTaskStepExecuteDao.get( dataCollectionTaskStepExecute.getStepExecuteId() );
    }
}