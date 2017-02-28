package com.huge.ihos.system.systemManager.busiprocess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.busiprocess.dao.BusiProcessLogDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessLogManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
@Service("busiProcessLogManager")
public class BusiProcessLogManagerImpl extends GenericManagerImpl<BusiProcessLog, Long> implements BusiProcessLogManager {
    private BusiProcessLogDao businessProcessLogDao;

    @Autowired
    public BusiProcessLogManagerImpl(BusiProcessLogDao businessProcessLogDao) {
        super(businessProcessLogDao);
        this.businessProcessLogDao = businessProcessLogDao;
    }
    
    public JQueryPager getBusinessProcessLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessProcessLogDao.getBusinessProcessLogCriteria(paginatedList,filters);
	}
}