package com.huge.ihos.system.systemManager.dataprivilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.dataprivilege.dao.DataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.dao.PrivilegeClassDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.service.DataPrivilegeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("dataPrivilegeManager")
public class DataPrivilegeManagerImpl extends GenericManagerImpl<DataPrivilege, String> implements DataPrivilegeManager {
    private DataPrivilegeDao dataPrivilegeDao;
    private PrivilegeClassDao privilegeClassDao;

    public PrivilegeClassDao getPrivilegeClassDao() {
		return privilegeClassDao;
	}

    @Autowired
	public void setPrivilegeClassDao(PrivilegeClassDao privilegeClassDao) {
		this.privilegeClassDao = privilegeClassDao;
	}

	@Autowired
    public DataPrivilegeManagerImpl(DataPrivilegeDao dataPrivilegeDao) {
        super(dataPrivilegeDao);
        this.dataPrivilegeDao = dataPrivilegeDao;
    }
    
    public JQueryPager getDataPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return dataPrivilegeDao.getDataPrivilegeCriteria(paginatedList,filters);
	}

	@Override
	public void deleteByRoleIdAndClass(String roleId, String classCode) {
		dataPrivilegeDao.deleteByRoleIdAndClass(roleId,privilegeClassDao.get(classCode));
	}

	@Override
	public List<DataPrivilege> findByRoleIdAndClass(String roleId,
			String classCode) {
		return dataPrivilegeDao.getDataPrivilegesByRole(roleId, classCode);
	}
}