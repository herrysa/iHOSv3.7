package com.huge.ihos.system.datacollection.service;

import java.util.List;

import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DataCollectionTaskStepExecuteManager
    extends GenericManager<DataCollectionTaskStepExecute, Long> {
    public JQueryPager getDataCollectionTaskStepExecuteCriteria( final JQueryPager paginatedList );

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( final JQueryPager paginatedList, Long taskId );

    public void clearStepExecuteByTask( DataCollectionTask task );

    public String updateDisabled( String id, boolean value );

    public String getStepExecuteIsUsed();

    public int getStepDefineUsedCount( Long stepDefineId );

    public DataCollectionTaskStepExecute getByTaskExecIdAndStepDefineId( Long taskExecId, Long stepDefineId );
    
    public List getByTaskExecId( Long taskExecId );
}