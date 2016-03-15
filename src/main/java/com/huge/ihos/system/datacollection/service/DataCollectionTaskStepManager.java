package com.huge.ihos.system.datacollection.service;

import java.util.List;

import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DataCollectionTaskStepManager
    extends GenericManager<DataCollectionTaskStep, Long> {
    public JQueryPager getDataCollectionTaskStepCriteria( final JQueryPager paginatedList, Long parentId );

    public List getAllByDefineId( Long defineId );
}