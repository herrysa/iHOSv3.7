package com.huge.ihos.system.systemManager.searchButtonPriv.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivDetailManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("buttonPrivDetailManager")
public class ButtonPrivDetailManagerImpl extends GenericManagerImpl<ButtonPrivDetail, String> implements ButtonPrivDetailManager {
    private ButtonPrivDetailDao buttonPrivDetailDao;
    private RoleDao roleDao;
    private ButtonPrivUserDao buttonPrivUserDao;
    private ButtonPrivUserDetailDao buttonPrivUserDetailDao;

    @Autowired
    public ButtonPrivDetailManagerImpl(ButtonPrivDetailDao buttonPrivDetailDao) {
        super(buttonPrivDetailDao);
        this.buttonPrivDetailDao = buttonPrivDetailDao;
    }
    
    public JQueryPager getButtonPrivDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return buttonPrivDetailDao.getButtonPrivDetailCriteria(paginatedList,filters);
	}
    
    @Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
    @Autowired
	public void setButtonPrivUserDao(
			ButtonPrivUserDao buttonPrivUserDao) {
		this.buttonPrivUserDao = buttonPrivUserDao;
	}
    @Autowired
	public void setButtonPrivUserDetailDao(
			ButtonPrivUserDetailDao buttonPrivUserDetailDao) {
		this.buttonPrivUserDetailDao = buttonPrivUserDetailDao;
	}

	@Override
	public void cascadeUpdate(List<ButtonPrivDetail> searchButtoPrivDetails) {
		Long roleId = -999L;
		Set<User> users = null;
		List<ButtonPrivUser> sbpus = null;
		Set<ButtonPrivUserDetail> sbpuds = null;
		for(ButtonPrivDetail sbpd:searchButtoPrivDetails){
			buttonPrivDetailDao.save(sbpd);
			roleId = Long.parseLong(sbpd.getButtonPriv().getRoleId());
			users = roleDao.get(roleId).getUsers();
			for(User user:users){
				sbpus = buttonPrivUserDao.findByUser(""+user.getId());
				for(ButtonPrivUser sbpu:sbpus){
					sbpuds = sbpu.getButtonPrivUserDetails();
					for(ButtonPrivUserDetail sbpud:sbpuds){
						if(sbpud.getButtonId().equals(sbpd.getButtonId())){
//							if(sbpd.getRight()){
//								sbpud.setRight(sbpud.getRight()||sbpd.getRight());
//							}else{
//								sbpud.setRight(sbpud.getRight()&&sbpd.getRight());
//							}
							buttonPrivUserDetailDao.save(sbpud);
						}
					}
				}
			}
		}
	}
    
    
}