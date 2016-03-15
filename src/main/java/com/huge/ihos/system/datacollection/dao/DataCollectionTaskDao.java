package com.huge.ihos.system.datacollection.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataCollectionTask table.
 */
public interface DataCollectionTaskDao
    extends GenericDao<DataCollectionTask, Long> {

    public JQueryPager getDataCollectionTaskCriteria( final JQueryPager paginatedList );

    public JQueryPager getDataCollectionCompleteTaskCriteria( final JQueryPager paginatedList, String period );

    public JQueryPager getDataCollectionRemainTaskCriteria( final JQueryPager paginatedList, String period );

    public Integer getPeriodTaskCount( String period );

    public Integer getPeriodCompleteTaskNum( String period );

    public Integer getPeriodRemainTaskNum( String period );

    public void clearPeriodTask( String period );

    public void execStepRemotePreProcess( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepPreProcess( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepVerify( DataCollectionTask task, DataCollectionTaskStep step );

    public String execStepPrompt( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepImport( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepOther( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepStoreProcedure( DataCollectionTask task, DataCollectionTaskStep step );

    public void execStepCollection( DataCollectionTask task, DataCollectionTaskStep step );

    public String updateDisabled( String id, boolean value );

    public int getTaskDefineUsedCount( Long taskDefineId );

    public DataCollectionTask getByePeriodCodeAndDefineId( String periodCode, Long defineId );
    
    public List<DataCollectionTask> getRemainTasks(String period);
    
    public List<DataCollectionTask> getInUsed();
    
    public List<DataCollectionTask> getByPeriod(String period);
}