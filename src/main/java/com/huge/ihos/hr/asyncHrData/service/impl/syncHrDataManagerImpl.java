package com.huge.ihos.hr.asyncHrData.service.impl;

import java.util.List;
import com.huge.ihos.hr.asyncHrData.dao.syncHrDataDao;
import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.ihos.hr.asyncHrData.service.syncHrDataManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("syncHrDataManager")
public class syncHrDataManagerImpl extends GenericManagerImpl<syncHrData, String> implements syncHrDataManager {
    private syncHrDataDao syncHrDataDao;

    @Autowired
    public syncHrDataManagerImpl(syncHrDataDao syncHrDataDao) {
        super(syncHrDataDao);
        this.syncHrDataDao = syncHrDataDao;
    }
    
    public JQueryPager getsyncHrDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return syncHrDataDao.getsyncHrDataCriteria(paginatedList,filters);
	}
}