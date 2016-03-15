package com.huge.ihos.system.datacollection.service;

import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DataCollectionTaskDefineManager
    extends GenericManager<DataCollectionTaskDefine, Long> {
    public JQueryPager getDataCollectionTaskDefineCriteria( final JQueryPager paginatedList );

    public Integer getAllTaskDefineCount();
    
    public boolean isUesedDataSource(Long dataSourceId);
    
    public DataCollectionTaskDefine getByNameDefine(String name);
}