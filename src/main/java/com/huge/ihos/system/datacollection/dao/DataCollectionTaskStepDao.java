package com.huge.ihos.system.datacollection.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataCollectionTaskStep table.
 */
public interface DataCollectionTaskStepDao
    extends GenericDao<DataCollectionTaskStep, Long> {

    public JQueryPager getDataCollectionTaskStepCriteria( final JQueryPager paginatedList, Long parentId );

    public List getAllByDefineId( Long defineId );
}