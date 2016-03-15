package com.huge.ihos.system.datacollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ecis.inter.helper.AbstractImportService;
import com.huge.ihos.system.datacollection.dao.DataSourceDefineDao;
import com.huge.ihos.system.datacollection.dao.DataSourceTypeDao;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.ihos.system.datacollection.service.DataSourceDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dataSourceDefineManager" )
public class DataSourceDefineManagerImpl
    extends GenericManagerImpl<DataSourceDefine, Long>
    implements DataSourceDefineManager {
    DataSourceDefineDao dataSourceDefineDao;

    DataSourceTypeDao dataSourceTypeDao;

    public DataSourceTypeDao getDataSourceTypeDao() {
        return dataSourceTypeDao;
    }

    @Autowired
    public void setDataSourceTypeDao( DataSourceTypeDao dataSourceTypeDao ) {
        this.dataSourceTypeDao = dataSourceTypeDao;
    }

    @Autowired
    public DataSourceDefineManagerImpl( DataSourceDefineDao dataSourceDefineDao ) {
        super( dataSourceDefineDao );
        this.dataSourceDefineDao = dataSourceDefineDao;
    }

    public JQueryPager getDataSourceDefineCriteria( JQueryPager paginatedList ) {
        return dataSourceDefineDao.getDataSourceDefineCriteria( paginatedList );
    }

    public boolean connectionTest( DataSourceDefine dataSourceDefine ) {
        boolean result = false;
        try {
            Long dsTypeId = dataSourceDefine.getDataSourceType().getDataSourceTypeId();
            DataSourceType dst = this.dataSourceTypeDao.get( dsTypeId );
            String helperName = dst.getHelperClassName();

            AbstractImportService ais = (AbstractImportService) Class.forName( helperName ).newInstance();
            result = ais.dataSourceTest( dataSourceDefine.getUrl(), dataSourceDefine.getUserName(), dataSourceDefine.getPassword() );

        }
        catch ( Exception e ) {
            e.printStackTrace();
            result = false;
        }
        finally {

        }
        return result;
    }

}
