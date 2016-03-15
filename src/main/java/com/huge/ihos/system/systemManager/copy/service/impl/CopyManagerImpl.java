package com.huge.ihos.system.systemManager.copy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.copy.dao.CopyDao;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("copyManager")
public class CopyManagerImpl extends GenericManagerImpl<Copy, String> implements CopyManager {
    private CopyDao copyDao;
    private UserManager userManager;

    public UserManager getUserManager() {
		return userManager;
	}
    @Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
    @Autowired
    public CopyManagerImpl(CopyDao copyDao) {
        super(copyDao);
        this.copyDao = copyDao;
    }
    
    public JQueryPager getCopyCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return copyDao.getCopyCriteria(paginatedList,filters);
	}

	@Override
	public List<Copy> getRightCopy(Long userId) {
		List<String> dataprivi = new ArrayList<String>();
		/*List<UserDataPrivilegeBean> userOrgDataprivieges = userManager.getDataPrivatesByClassCode(""+userId, "02");
		for(UserDataPrivilegeBean userDataPrivilegeBean : userOrgDataprivieges){
			dataprivi.add(userDataPrivilegeBean.getItem());
		}*/
		
		return copyDao.getRightCopy(dataprivi);
	}
	@Override
	public boolean isNewCopy(String copyCode) {
		Copy copy = this.getCopyByCode(copyCode);
		if(copy == null){
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Copy getCopyByCode(String copyCode){
		return copyDao.getCopyByCode(copyCode);
	}
}