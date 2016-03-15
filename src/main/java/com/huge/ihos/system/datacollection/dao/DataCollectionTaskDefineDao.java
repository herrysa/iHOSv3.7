package com.huge.ihos.system.datacollection.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataCollectionTaskDefine table.
 */
public interface DataCollectionTaskDefineDao
    extends GenericDao<DataCollectionTaskDefine, Long> {

    public JQueryPager getDataCollectionTaskDefineCriteria( final JQueryPager paginatedList );

    public Integer getAllTaskDefineCount();
    
    public List<DataCollectionTaskDefine> getBySourceId(Long sourceDefineId);
    
    public List<DataCollectionTaskDefine> getByDept(String deptId);
    
    public List<DataCollectionTaskDefine> getByName(String name);
}