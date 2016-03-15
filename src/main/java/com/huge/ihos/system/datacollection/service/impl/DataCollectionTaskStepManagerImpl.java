package com.huge.ihos.system.datacollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskStepManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dataCollectionTaskStepManager" )
public class DataCollectionTaskStepManagerImpl
    extends GenericManagerImpl<DataCollectionTaskStep, Long>
    implements DataCollectionTaskStepManager {
    DataCollectionTaskStepDao dataCollectionTaskStepDao;

    @Autowired
    public DataCollectionTaskStepManagerImpl( DataCollectionTaskStepDao dataCollectionTaskStepDao ) {
        super( dataCollectionTaskStepDao );
        this.dataCollectionTaskStepDao = dataCollectionTaskStepDao;
    }

    public JQueryPager getDataCollectionTaskStepCriteria( JQueryPager paginatedList, Long parentId ) {
        return dataCollectionTaskStepDao.getDataCollectionTaskStepCriteria( paginatedList, parentId );
    }

    public List getAllByDefineId( Long defineId ) {
        return dataCollectionTaskStepDao.getAllByDefineId( defineId );
    }

}