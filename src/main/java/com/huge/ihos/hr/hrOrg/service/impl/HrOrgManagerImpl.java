package com.huge.ihos.hr.hrOrg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrOrg.dao.HrOrgDao;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrOrgManager")
public class HrOrgManagerImpl extends GenericManagerImpl<HrOrg, String> implements HrOrgManager {
    private HrOrgDao hrOrgDao;

    @Autowired
    public HrOrgManagerImpl(HrOrgDao hrOrgDao) {
        super(hrOrgDao);
        this.hrOrgDao = hrOrgDao;
    }
    
    public JQueryPager getHrOrgCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrOrgDao.getHrOrgCriteria(paginatedList,filters);
	}
    
	@Override
	public List<HrOrg> getAllAvailable() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("NES_orgCode","XT"));
		filters.add(new PropertyFilter("OAS_orgCode","orgCode"));
		List<HrOrg> hrOrgList = this.getByFilters(filters);
		if(hrOrgList!=null && !hrOrgList.isEmpty()){
			return hrOrgList;
		}
		return null;
	}

	@Override
	public String getAllAvailableString() {
		List<HrOrg> hrOrgList = this.getAllAvailable();
		if(hrOrgList!=null){
			String orgCodes = "";
			for(HrOrg hrOrg:hrOrgList){
				orgCodes += hrOrg.getOrgCode()+",";
			}
			orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
			return orgCodes;
		}
		return null;
	}

	@Override
	public String getAllOrgCodesInOrg(String orgCode) {
		String orgCodes = orgCode;
		List<HrOrg> hrOrgList = getAllDescendants(orgCode);
		if(!hrOrgList.isEmpty()){
			for(HrOrg hrOrg:hrOrgList){
				orgCodes += ","+hrOrg.getOrgCode();
			}
		}
		return orgCodes;
	}
	
	@Override
	public List<HrOrg> getAllDescendants(String orgCode){
		List<HrOrg> hrOrgList = new ArrayList<HrOrg>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentOrgCode.orgCode",orgCode));
		List<HrOrg> childHrOrgList = this.getByFilters(pfs);
		if(childHrOrgList!=null && !childHrOrgList.isEmpty()){
			hrOrgList.addAll(childHrOrgList);
			for(HrOrg hrOrg:childHrOrgList){
				List<HrOrg> childHrOrgs = getAllDescendants(hrOrg.getOrgCode());
				hrOrgList.addAll(childHrOrgs);
			}
		}
		return hrOrgList;
	}

	@Override
	public List<HrOrg> getAllParentHrOrg(String orgCode) {
		List<HrOrg> hrOrgList = new ArrayList<HrOrg>();
		HrOrg hrOrg = this.get(orgCode);
		HrOrg pHrOrg = hrOrg.getParentOrgCode();
		if(pHrOrg!=null && OtherUtil.measureNotNull(pHrOrg.getOrgCode())){
			hrOrgList.add(pHrOrg);
			List<HrOrg> pHrOrgList = getAllParentHrOrg(pHrOrg.getOrgCode());
			hrOrgList.addAll(pHrOrgList);
		}
		return hrOrgList;
	} 
}