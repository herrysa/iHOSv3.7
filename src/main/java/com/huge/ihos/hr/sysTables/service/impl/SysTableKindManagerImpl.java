package com.huge.ihos.hr.sysTables.service.impl;

import java.util.List;

import com.huge.ihos.hr.sysTables.dao.SysTableKindDao;
import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.ihos.hr.sysTables.service.SysTableKindManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("sysTableKindManager")
public class SysTableKindManagerImpl extends GenericManagerImpl<SysTableKind, String> implements SysTableKindManager {
    private SysTableKindDao sysTableKindDao;

    @Autowired
    public SysTableKindManagerImpl(SysTableKindDao sysTableKindDao) {
        super(sysTableKindDao);
        this.sysTableKindDao = sysTableKindDao;
    }
    
    public JQueryPager getSysTableKindCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysTableKindDao.getSysTableKindCriteria(paginatedList,filters);
	}
    @Override
    public List<SysTableKind> getFullSysTableKind(String orgCode){
    	return sysTableKindDao.getFullSysTableKind(orgCode);
    }
}