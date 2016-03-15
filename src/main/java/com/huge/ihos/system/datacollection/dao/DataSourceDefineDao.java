package com.huge.ihos.system.datacollection.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataSourceDefine table.
 */
public interface DataSourceDefineDao
    extends GenericDao<DataSourceDefine, Long> {

    public JQueryPager getDataSourceDefineCriteria( final JQueryPager paginatedList );
}