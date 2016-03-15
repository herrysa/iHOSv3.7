package com.huge.ihos.system.datacollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepExecuteDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepExecuteManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dataCollectionTaskStepExecuteManager" )
public class DataCollectionTaskStepExecuteManagerImpl
    extends GenericManagerImpl<DataCollectionTaskStepExecute, Long>
    implements DataCollectionTaskStepExecuteManager {
    DataCollectionTaskStepExecuteDao dataCollectionTaskStepExecuteDao;

    @Autowired
    public DataCollectionTaskStepExecuteManagerImpl( DataCollectionTaskStepExecuteDao dataCollectionTaskStepExecuteDao ) {
        super( dataCollectionTaskStepExecuteDao );
        this.dataCollectionTaskStepExecuteDao = dataCollectionTaskStepExecuteDao;
    }

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( JQueryPager paginatedList ) {
        return dataCollectionTaskStepExecuteDao.getDataCollectionTaskStepExecuteCriteria( paginatedList );
    }

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( JQueryPager paginatedList, Long taskId ) {
        return dataCollectionTaskStepExecuteDao.getDataCollectionTaskStepExecuteCriteria( paginatedList, taskId );
    }

    public void clearStepExecuteByTask( DataCollectionTask task ) {
        dataCollectionTaskStepExecuteDao.clearStepExecuteByTask( task );
    }

    public String updateDisabled( String id, boolean value ) {
        return dataCollectionTaskStepExecuteDao.updateDisabled( id, value );
    }

    public String getStepExecuteIsUsed() {
        return dataCollectionTaskStepExecuteDao.getStepExecuteIsUsed();
    }

    public int getStepDefineUsedCount( Long stepDefineId ) {
        return dataCollectionTaskStepExecuteDao.getStepDefineUsedCount( stepDefineId );
    }

    public DataCollectionTaskStepExecute getByTaskExecIdAndStepDefineId( Long taskExecId, Long stepDefineId ) {
        return this.dataCollectionTaskStepExecuteDao.getByTaskExecIdAndStepDefineId( taskExecId, stepDefineId );
    }

	@Override
	public List getByTaskExecId(Long taskExecId) {
		return dataCollectionTaskStepExecuteDao.getByTaskExecId(taskExecId);
	}
}