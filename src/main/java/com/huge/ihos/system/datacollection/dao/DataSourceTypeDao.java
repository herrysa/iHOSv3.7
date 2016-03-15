package com.huge.ihos.system.datacollection.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DataSourceType table.
 */
public interface DataSourceTypeDao
    extends GenericDao<DataSourceType, Long> {

    public JQueryPager getDataSourceTypeCriteria( final JQueryPager paginatedList );
}