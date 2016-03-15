package com.huge.ihos.system.systemManager.dataprivilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.dataprivilege.dao.PrivilegeClassDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.service.PrivilegeClassManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("privilegeClassManager")
public class PrivilegeClassManagerImpl extends GenericManagerImpl<PrivilegeClass, String> implements PrivilegeClassManager {
    private PrivilegeClassDao privilegeClassDao;

    @Autowired
    public PrivilegeClassManagerImpl(PrivilegeClassDao privilegeClassDao) {
        super(privilegeClassDao);
        this.privilegeClassDao = privilegeClassDao;
    }
    
    public JQueryPager getPrivilegeClassCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return privilegeClassDao.getPrivilegeClassCriteria(paginatedList,filters);
	}

	@Override
	public List<PrivilegeClass> findEnabledClass() {
		return privilegeClassDao.findEnabledClass();
	}
	@Override
	public List<PrivilegeClass> getAllByCode(String classCode) {
		return this.privilegeClassDao.getAllByCode(classCode);
	}
}