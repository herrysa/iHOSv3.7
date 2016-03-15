package com.huge.ihos.system.systemManager.dataprivilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.dataprivilege.dao.PrivilegeClassDao;
import com.huge.ihos.system.systemManager.dataprivilege.dao.UserDataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.service.UserDataPrivilegeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("userDataPrivilegeManager")
public class UserDataPrivilegeManagerImpl extends GenericManagerImpl<UserDataPrivilege, String> implements UserDataPrivilegeManager {
    private UserDataPrivilegeDao userDataPrivilegeDao;
    private PrivilegeClassDao privilegeClassDao;

    public PrivilegeClassDao getPrivilegeClassDao() {
		return privilegeClassDao;
	}

    @Autowired
	public void setPrivilegeClassDao(PrivilegeClassDao privilegeClassDao) {
		this.privilegeClassDao = privilegeClassDao;
	}

    @Autowired
    public UserDataPrivilegeManagerImpl(UserDataPrivilegeDao userDataPrivilegeDao) {
        super(userDataPrivilegeDao);
        this.userDataPrivilegeDao = userDataPrivilegeDao;
    }
    
    public JQueryPager getUserDataPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return userDataPrivilegeDao.getUserDataPrivilegeCriteria(paginatedList,filters);
	}

	@Override
	public void deleteByUserIdAndClass(String userId, String classCode,String exceptIds) {
		userDataPrivilegeDao.deleteByUserIdAndClass(userId, privilegeClassDao.get(classCode),exceptIds);
	}

	@Override
	public List<UserDataPrivilege> findByUserIdAndClass(String userId,
			String classCode) {
		return userDataPrivilegeDao.findByUserIdAndClass(userId, classCode);
	}
}