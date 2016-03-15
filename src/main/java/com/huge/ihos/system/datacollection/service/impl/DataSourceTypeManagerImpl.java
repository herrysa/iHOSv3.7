package com.huge.ihos.system.datacollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.dao.DataSourceTypeDao;
import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.ihos.system.datacollection.service.DataSourceTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dataSourceTypeManager" )
public class DataSourceTypeManagerImpl
    extends GenericManagerImpl<DataSourceType, Long>
    implements DataSourceTypeManager {
    DataSourceTypeDao dataSourceTypeDao;

    @Autowired
    public DataSourceTypeManagerImpl( DataSourceTypeDao dataSourceTypeDao ) {
        super( dataSourceTypeDao );
        this.dataSourceTypeDao = dataSourceTypeDao;
    }

    public JQueryPager getDataSourceTypeCriteria( JQueryPager paginatedList ) {
        return dataSourceTypeDao.getDataSourceTypeCriteria( paginatedList );
    }

}