package com.huge.ihos.system.datacollection.service;

import java.util.List;
import java.util.Map;

import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DataCollectionTaskManager
    extends GenericManager<DataCollectionTask, Long> {
    public JQueryPager getDataCollectionTaskCriteria( final JQueryPager paginatedList );

    public JQueryPager getDataCollectionCompleteTaskCriteria( final JQueryPager paginatedList, String period );

    public JQueryPager getDataCollectionRemainTaskCriteria( final JQueryPager paginatedList, String period );

    //public Integer getCurrentPeriodTaskCount();

    public Integer getPeriodTaskCount( String period );

    public Integer getPeriodCompleteTaskNum( String period );

    public Integer getPeriodRemainTaskNum( String period );

    public void createPeriodTask( String period ,String deptId);

    public void clearPeriodTask( String period );

    public void prepareExecuteTask( Long taskId );

    public Map executeTask( Long taskId, Map logsMap );

    public String updateDisabled( String id, boolean value );

    public int getTaskDefineUsedCount( Long taskDefineId );

    public void createSinglePeriodTask( String periodCode, Long defineId );

    public DataCollectionTask getByePeriodCodeAndDefineId( String periodCode, Long defineId );
    
    public List<DataCollectionTask> getRemainTasks(String period);
    
    public boolean isAllowColsePeriod(String period);
    
    public List<DataCollectionTask> getInUsed();
    
    public List<DataCollectionTask> getByPeriod(String period);
    
    public JQueryPager getDataCollectionTasksForGrid( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );
}