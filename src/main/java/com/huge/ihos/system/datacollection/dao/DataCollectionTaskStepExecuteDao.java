package com.huge.ihos.system.datacollection.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataCollectionTaskStepExecute table.
 */
public interface DataCollectionTaskStepExecuteDao
    extends GenericDao<DataCollectionTaskStepExecute, Long> {

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( final JQueryPager paginatedList );

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( final JQueryPager paginatedList, Long taskId );

    public void clearStepExecuteByTask( DataCollectionTask task );

    public List getByTaskExecId( Long taskExecId );

    public DataCollectionTaskStepExecute getByTaskExecIdAndStepDefineId( Long taskExecId, Long stepDefineId );

    public String updateDisabled( String id, boolean value );

    public String getStepExecuteIsUsed();

    public int getStepDefineUsedCount( Long stepDefineId );

}