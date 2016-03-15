package com.huge.ihos.system.datacollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDefineDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskDefine;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dataCollectionTaskDefineManager" )
public class DataCollectionTaskDefineManagerImpl
    extends GenericManagerImpl<DataCollectionTaskDefine, Long>
    implements DataCollectionTaskDefineManager {
    DataCollectionTaskDefineDao dataCollectionTaskDefineDao;

    @Autowired
    public DataCollectionTaskDefineManagerImpl( DataCollectionTaskDefineDao dataCollectionTaskDefineDao ) {
        super( dataCollectionTaskDefineDao );
        this.dataCollectionTaskDefineDao = dataCollectionTaskDefineDao;
    }

    public JQueryPager getDataCollectionTaskDefineCriteria( JQueryPager paginatedList ) {
        return dataCollectionTaskDefineDao.getDataCollectionTaskDefineCriteria( paginatedList );
    }

    public Integer getAllTaskDefineCount() {

        return dataCollectionTaskDefineDao.getAllTaskDefineCount();
    }
    
    @Override
	public boolean isUesedDataSource(Long dataSourceId) {
		List<DataCollectionTaskDefine> dataCollectionTaskDefineList = dataCollectionTaskDefineDao.getBySourceId(dataSourceId);
		if(dataCollectionTaskDefineList!=null&&dataCollectionTaskDefineList.size()!=0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public DataCollectionTaskDefine getByNameDefine(String name) {
		List<DataCollectionTaskDefine> dataCollectionTaskDefines = this.dataCollectionTaskDefineDao.getByName(name);
		if(dataCollectionTaskDefines.isEmpty()) {
			return null;
		} else {
			return dataCollectionTaskDefines.get(0);
		}
	}
    
}