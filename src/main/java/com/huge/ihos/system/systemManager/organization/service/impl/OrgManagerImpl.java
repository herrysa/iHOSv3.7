package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.dao.BranchDao;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.OrgDao;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("orgManager")
public class OrgManagerImpl extends GenericManagerImpl<Org, String> implements OrgManager {
    private OrgDao orgDao;
    private UserManager userManager;
    private BranchDao branchDao;
    private DepartmentDao departmentDao;
    
    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

    public UserManager getUserManager() {
		return userManager;
	}
    @Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Autowired
    public OrgManagerImpl(OrgDao orgDao) {
        super(orgDao);
        this.orgDao = orgDao;
    }
	@Autowired
    public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}
    public JQueryPager getOrgCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return orgDao.getOrgCriteria(paginatedList,filters);
	}
    
	@Override
	public List<Org> getAllAvailable() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("NES_orgCode","XT"));
		filters.add(new PropertyFilter("OAS_orgCode",""));
		List<Org> orgList = this.getByFilters(filters);
		if(orgList!=null && !orgList.isEmpty()){
			return orgList;
		}
		return null;
	}
	@Override
	public List<Org> getRightOrg(Long userId) {
		List<String> dataprivi = new ArrayList<String>();
		/*List<UserDataPrivilegeBean> userOrgDataprivieges = userManager.getDataPrivatesByClassCode(""+userId, "01");
		for(UserDataPrivilegeBean userDataPrivilegeBean : userOrgDataprivieges){
			dataprivi.add(userDataPrivilegeBean.getItem());
		}*/
		
		return orgDao.getRightOrg(dataprivi);
	}
	@Override
	public List<Org> getOrgsByParent(String parentId) {
		return orgDao.getOrgsByParent(parentId) ;
	}
	@Override
	public boolean isNewOrg(String orgId) {
		Org org =  this.getOrgById(orgId);
		if(org == null){
			return true;
		} else {
			return false;
		}
	}
	
	public Org getOrgById(String orgId){
		return orgDao.getOrgById(orgId);
	}
	@Override
	public List<Org> getAllDescendants(String orgCode) {
		List<Org> orgList = new ArrayList<Org>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentOrgCode.orgCode",orgCode));
		pfs.add(new PropertyFilter("EQB_disabled","0"));
		List<Org> orgs = this.getByFilters(pfs);
		if(orgs!=null && orgs.size()>0){
			orgList.addAll(orgs);
			for(Org org:orgs){
				List<Org> childOrgs = getAllDescendants(org.getOrgCode());
				orgList.addAll(childOrgs);
			}
		}
		return orgList;
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		orgDao.disableOrgAfterSync(snapCode);
	}
	@Override
	public List<Org> getAllExcXT() {
		Criteria criteria = this.orgDao.getCriteria();
		criteria.add(Restrictions.ne("orgCode", "XT"));
		return criteria.list();
	}
	@Override
	public String checkDelete(String removeId) {
		List<Org> orgs = orgDao.getOrgsByParent(removeId);
		if(orgs != null && !orgs.isEmpty()) {
			return "org";
		}
		List<Branch> branches = branchDao.getByOrgCode(removeId);
		if(branches != null && !branches.isEmpty()) {
			return "branch";
		}
		List<Department> departments = departmentDao.getAllDeptByOrgCode(removeId, null);
		if(departments != null && !departments.isEmpty()) {
			return "department";
		}
		return null;
	}
}