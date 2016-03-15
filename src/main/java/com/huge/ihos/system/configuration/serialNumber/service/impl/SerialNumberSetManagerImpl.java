package com.huge.ihos.system.configuration.serialNumber.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberSetDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.ihos.system.configuration.serialNumber.service.SerialNumberSetManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("serialNumberSetManager")
public class SerialNumberSetManagerImpl extends GenericManagerImpl<SerialNumberSet, String> implements SerialNumberSetManager {
    private SerialNumberSetDao serialNumberSetDao;

    @Autowired
    public SerialNumberSetManagerImpl(SerialNumberSetDao serialNumberSetDao) {
        super(serialNumberSetDao);
        this.serialNumberSetDao = serialNumberSetDao;
    }
    
    public JQueryPager getSerialNumberSetCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return serialNumberSetDao.getSerialNumberSetCriteria(paginatedList,filters);
	}
}