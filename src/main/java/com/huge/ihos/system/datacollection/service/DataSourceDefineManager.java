package com.huge.ihos.system.datacollection.service;

import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DataSourceDefineManager
    extends GenericManager<DataSourceDefine, Long> {
    public JQueryPager getDataSourceDefineCriteria( final JQueryPager paginatedList );

    public boolean connectionTest( DataSourceDefine dataSourceDefine );
}