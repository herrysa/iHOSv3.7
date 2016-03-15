package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;

public class DataCollectionTaskDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataCollectionTaskDao dataCollectionTaskDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataCollectionTask() {
        DataCollectionTask dataCollectionTask = new DataCollectionTask();

        // enter all required fields
        dataCollectionTask.setStatus( "UuCcSyXuOdHfPhLzMzBx" );

        log.debug( "adding dataCollectionTask..." );
        dataCollectionTask = dataCollectionTaskDao.save( dataCollectionTask );

        dataCollectionTask = dataCollectionTaskDao.get( dataCollectionTask.getDataCollectionTaskId() );

        assertNotNull( dataCollectionTask.getDataCollectionTaskId() );

        log.debug( "removing dataCollectionTask..." );

        dataCollectionTaskDao.remove( dataCollectionTask.getDataCollectionTaskId() );

        // should throw DataAccessException 
        dataCollectionTaskDao.get( dataCollectionTask.getDataCollectionTaskId() );
    }

    /*  @Test
      public void testCount(){
      	
      	
      	int ptc =dataCollectionTaskDao.getPeriodTaskCount(1l);
      	int ptcc = dataCollectionTaskDao.getPeriodCompleteTaskNum(1l);
      	int ptrc = dataCollectionTaskDao.getPeriodRemainTaskNum(1l);
      	
      	System.out.print(ptc + " " + ptcc + " " + ptrc);
      	
      }
     */
    /*  @Test
      public void testClear(){
      	dataCollectionTaskDao.clearPeriodTask(1l);
      }*/
}