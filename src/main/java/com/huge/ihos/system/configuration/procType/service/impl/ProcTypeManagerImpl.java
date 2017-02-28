package com.huge.ihos.system.configuration.procType.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.procType.dao.ProcTypeDao;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.configuration.procType.service.ProcTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("procTypeManager")
public class ProcTypeManagerImpl extends GenericManagerImpl<ProcType, String> implements ProcTypeManager {
    private ProcTypeDao procTypeDao;

    @Autowired
    public ProcTypeManagerImpl(ProcTypeDao procTypeDao) {
        super(procTypeDao);
        this.procTypeDao = procTypeDao;
    }
    
    public JQueryPager getProcTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return procTypeDao.getProcTypeCriteria(paginatedList,filters);
	}

	@Override
	public ProcType getProcTypeByCode(String code) {
		return procTypeDao.getProcTypeByCode(code);
	}
    
}