package com.huge.ihos.system.datacollection.service;

import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DataSourceTypeManager
    extends GenericManager<DataSourceType, Long> {
    public JQueryPager getDataSourceTypeCriteria( final JQueryPager paginatedList );
}