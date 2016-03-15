package com.huge.ihos.hr.hrDeptment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentCurrentDao;
import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentSnapDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptNewManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.dao.HrPersonCurrentDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.system.systemManager.organization.dao.PersonTypeDao;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.util.TestTimer;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrDepartmentCurrentManager")
public class HrDepartmentCurrentManagerImpl extends GenericManagerImpl<HrDepartmentCurrent, String> implements HrDepartmentCurrentManager {
	private HrDepartmentCurrentDao hrDepartmentCurrentDao;
	private HrOrgManager hrOrgManager;
	private HrDeptNewManager hrDeptNewManager;
	private HrPersonCurrentDao hrPersonCurrentDao;
	private HrDepartmentSnapDao hrDepartmentSnapDao;
	private PersonTypeDao personTypeDao;

	@Autowired
	public void setPersonTypeDao(PersonTypeDao personTypeDao) {
		this.personTypeDao = personTypeDao;
	}
	@Autowired
	public void setHrDepartmentSnapDao(HrDepartmentSnapDao hrDepartmentSnapDao) {
		this.hrDepartmentSnapDao = hrDepartmentSnapDao;
	}

	@Autowired
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@Autowired
	public HrDepartmentCurrentManagerImpl(HrDepartmentCurrentDao hrDepartmentCurrentDao) {
		super(hrDepartmentCurrentDao);
		this.hrDepartmentCurrentDao = hrDepartmentCurrentDao;
	}
	@Autowired
	public void setHrDeptNewManager(HrDeptNewManager hrDeptNewManager) {
		this.hrDeptNewManager = hrDeptNewManager;
	}
	@Autowired
	public void setHrPersonCurrentDao(HrPersonCurrentDao hrPersonCurrentDao) {
		this.hrPersonCurrentDao = hrPersonCurrentDao;
	}
	public JQueryPager getHrDepartmentCurrentCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return hrDepartmentCurrentDao.getHrDepartmentCurrentCriteria(paginatedList, filters);
	}
	public JQueryPager getHrDepartmentCriteriaForSetPlan(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return hrDepartmentCurrentDao.getHrDepartmentCriteriaForSetPlan(paginatedList, filters);
	}

	@Override
	public List<HrDepartmentCurrent> getAllDescendants(String deptId) {
		List<HrDepartmentCurrent> hdcList = new ArrayList<HrDepartmentCurrent>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentDept.departmentId", deptId));
		List<HrDepartmentCurrent> hdss = this.getByFilters(pfs);
		if (hdss != null && !hdss.isEmpty()) {
			hdcList.addAll(hdss);
			for (HrDepartmentCurrent hdc : hdss) {
				List<HrDepartmentCurrent> hrDCs = getAllDescendants(hdc.getDepartmentId());
				hdcList.addAll(hrDCs);
			}
		}
		return hdcList;
	}

	@Override
	public String getAllDeptIds(String orgCode, String deptId) {
		String deptIds = null;
		if (orgCode != null) {
			deptIds = "";
			List<HrOrg> orgList = hrOrgManager.getAllDescendants(orgCode);
			String orgCodes = orgCode;
			for (HrOrg org : orgList) {
				orgCodes += "," + org.getOrgCode();
			}
			orgList = null;
			List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
			pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
			List<HrDepartmentCurrent> hdss = this.getByFilters(pfs);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentCurrent hds : hdss) {
					deptIds += hds.getDepartmentId() + ",";
				}
				deptIds = OtherUtil.subStrEnd(deptIds, ",");
			}
		} else if (deptId != null) {
			deptIds = deptId;
			List<HrDepartmentCurrent> hdss = this.getAllDescendants(deptId);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentCurrent hds : hdss) {
					deptIds += "," + hds.getDepartmentId();
				}
			}
		}
		return deptIds;
	}
	@Override
	public String[] getAllDeptCodes(String orgCode,String deptId,Boolean showDisableDept){
		String deptCodes = null;
		String orgCodes = null;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<HrDeptNew> hrDeptNews = null;
		if (orgCode != null) {
			deptCodes = "";
			List<HrOrg> orgList = hrOrgManager.getAllDescendants(orgCode);
			orgCodes = orgCode;
			for (HrOrg org : orgList) {
				orgCodes += "," + org.getOrgCode();
			}
			List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
			pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
			if(!showDisableDept){
				pfs.add(new PropertyFilter("EQB_disabled", "0"));
			}
			List<HrDepartmentCurrent> hdss = this.getByFilters(pfs);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentCurrent hds : hdss) {
					deptCodes += hds.getDeptCode() + ",";
				}
			}
			filters.clear();
			filters.add(new PropertyFilter("NEI_state","3"));
			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			hrDeptNews = hrDeptNewManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(hrDeptNews)&&!hrDeptNews.isEmpty()){
				for(HrDeptNew hrdeptnew:hrDeptNews){
					if(!showDisableDept&&hrdeptnew.getParentDept()!=null&&hrdeptnew.getParentDept().getDisabled()){
						continue;
					}
					deptCodes += hrdeptnew.getDeptCode()+"," ;
				}
			}
		} else if (deptId != null) {
			deptCodes = "";
			HrDepartmentCurrent hdt=hrDepartmentCurrentDao.get(deptId);
			orgCodes = hdt.getOrgCode();
			String deptIds = hdt.getDepartmentId();
			deptCodes = hdt.getDeptCode()+",";
			List<HrDepartmentCurrent> hdss = this.getAllDescendants(deptId);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentCurrent hds : hdss) {
					if(!showDisableDept&&hds.getDisabled()){
						continue;
					}
					deptCodes += hds.getDeptCode()+",";
					deptIds += "," + hds.getDepartmentId();
				}
			}
			filters.clear();
			filters.add(new PropertyFilter("NEI_state","3"));
			filters.add(new PropertyFilter("INS_parentDept.departmentId",deptIds));
			hrDeptNews=hrDeptNewManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(hrDeptNews)&&!hrDeptNews.isEmpty()){
				for(HrDeptNew hrdeptnew:hrDeptNews){
					if(!showDisableDept&&hrdeptnew.getParentDept()!=null&&hrdeptnew.getParentDept().getDisabled()){
						continue;
					}
					deptCodes += hrdeptnew.getDeptCode()+",";
				}
			}
		}else{
			deptCodes = "";
			orgCodes = hrOrgManager.getAllAvailableString();
			if(OtherUtil.measureNull(orgCodes)){
				orgCodes = "";
			}
			List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
			pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
			if(!showDisableDept){
				pfs.add(new PropertyFilter("EQB_disabled", "0"));
			}
			List<HrDepartmentCurrent> hdss = this.getByFilters(pfs);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentCurrent hds : hdss) {
					deptCodes +=  hds.getDeptCode()+"," ;
				}
			}
			filters.clear();
			filters.add(new PropertyFilter("NEI_state","3"));
			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			hrDeptNews = hrDeptNewManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(hrDeptNews)&&!hrDeptNews.isEmpty()){
				for(HrDeptNew hrdeptnew:hrDeptNews){
					if(!showDisableDept&&hrdeptnew.getParentDept()!=null&&hrdeptnew.getParentDept().getDisabled()){
						continue;
					}
					deptCodes += hrdeptnew.getDeptCode()+",";
				}
			}
		}
		deptCodes = OtherUtil.subStrEnd(deptCodes, ",");
		String[] result = new String[2];
		result[0] = orgCodes;
		result[1] = deptCodes;
		return result;
	}
	@Override
	public String checkLockHrDepartmentCurrent(String deptId,String[] checkLockStates) {
		HrDepartmentCurrent hrDepartmentCurrent = hrDepartmentCurrentDao.get(deptId);
		String messtr = null;
		if (hrDepartmentCurrent != null) {
			String lockState = hrDepartmentCurrent.getLockState();
			if (OtherUtil.measureNotNull(lockState)) {
				String[] states = lockState.split(",");
				List<String> checkStatelist = new ArrayList<String>();
				checkStatelist = Arrays.asList(checkLockStates);
				for (int stateIndex = 0; stateIndex < states.length; stateIndex++) {
					String stateTemp = states[stateIndex];
					if (checkStatelist.contains(stateTemp)) {
						if (messtr == null) {
							messtr = stateTemp;
						} else {
							messtr += "," + stateTemp;
						}
					}
				}
			}
		}
		return messtr;
	}

	@Override
	public void computePersonCount() {
		//hrDepartmentCurrentDao.flusSession();
		TestTimer tt =new TestTimer("computePersonCount");
		tt.begin();
		List<PropertyFilter> notLeafDeptFilters = new ArrayList<PropertyFilter>();
		notLeafDeptFilters.add(new PropertyFilter("EQB_leaf","false"));
		//notLeafDeptFilters.add(new PropertyFilter("EQB_disabled","false"));
		List<HrDepartmentCurrent> notLeafHrDeptCurrents = hrDepartmentCurrentDao.getByFilters(notLeafDeptFilters);
		for(int deptIndex=0;deptIndex<notLeafHrDeptCurrents.size();deptIndex++){
			HrDepartmentCurrent hrDepartmentCurrent = notLeafHrDeptCurrents.get(deptIndex);
			hrDepartmentCurrent.setRealCount(0);
			hrDepartmentCurrent.setRealCountAll(0);
			hrDepartmentCurrent.setRealZbCount(0);
			hrDepartmentCurrent.setDiffCount(0);
			hrDepartmentCurrentDao.save(hrDepartmentCurrent);
		}
		List<PropertyFilter> deptFilters = new ArrayList<PropertyFilter>();
		deptFilters.add(new PropertyFilter("EQB_leaf","true"));
		deptFilters.add(new PropertyFilter("EQB_disabled","false"));
		List<HrDepartmentCurrent> hrDepartmentCurrents = hrDepartmentCurrentDao.getByFilters(deptFilters);
		List<PropertyFilter> personTypeFilters = new ArrayList<PropertyFilter>();
		personTypeFilters.add(new PropertyFilter("EQS_code","PT0101"));
		List<PersonType> personTypes = personTypeDao.getByFilters(personTypeFilters);
		PersonType personType = null; 
		if(personTypes!=null&&personTypes.size()!=0){
			personType = personTypes.get(0);
		}else{
			personType =new PersonType();
			personType.setCode("PT0101");
			personType = personTypeDao.save(personType);
		}
		for(int deptIndex=0;deptIndex<hrDepartmentCurrents.size();deptIndex++){
			HrDepartmentCurrent hrDepartmentCurrent = hrDepartmentCurrents.get(deptIndex);
			List<PropertyFilter> personCountfilters = new ArrayList<PropertyFilter>();
			personCountfilters.add(new PropertyFilter("SQS_department.departmentId","dept_id='"+hrDepartmentCurrent.getDepartmentId()+"'"));
			int pc =  hrPersonCurrentDao.getCountByFilters(personCountfilters);
			personCountfilters.add(new PropertyFilter("EQB_disable","false"));
			//List<HrPersonCurrent> hrPersonCurrents = hrPersonCurrentDao.getByFilters(personCountfilters);
			int pcNoDisabled = hrPersonCurrentDao.getCountByFilters(personCountfilters);
			personCountfilters.add(new PropertyFilter("SQS_status.code","empType='"+personType.getId()+"'"));
			//hrPersonCurrents = hrPersonCurrentDao.getCountByFilters(personCountfilters);
			int zbPcNoDisabled = hrPersonCurrentDao.getCountByFilters(personCountfilters);
			hrDepartmentCurrent.setRealCount(pcNoDisabled);
			hrDepartmentCurrent.setRealCountAll(pc);
			hrDepartmentCurrent.setRealZbCount(zbPcNoDisabled);
			Integer planCount = hrDepartmentCurrent.getPlanCount();
			Integer diffCount = hrDepartmentCurrent.getDiffCount();
			if(planCount==null){
				planCount = 0;
			}
			if(diffCount==null){
				diffCount = 0;
			}
			diffCount =  planCount - hrDepartmentCurrent.getRealZbCount();
			if(diffCount<0){
				diffCount = 0;
			}
			hrDepartmentCurrent.setDiffCount(diffCount);
			hrDepartmentCurrentDao.save(hrDepartmentCurrent);
			setDeptPersonCount(hrDepartmentCurrent,hrDepartmentCurrent.getRealCount(),hrDepartmentCurrent.getRealZbCount(),hrDepartmentCurrent.getRealCountAll());
			//hrDepartmentCurrentDao.flusSession();
		}
		tt.done();
	}
	
	private void setDeptPersonCount(HrDepartmentCurrent dept,int addRealPersonCount,int addRealZbPersonCount,int addRealPersonCountAll){
		HrDepartmentCurrent pDept = dept.getParentDept();
		if(pDept!=null){
			Integer realPersonCount = pDept.getRealCount();
			Integer realCountAll = pDept.getRealCountAll();
			Integer realZbPersonCount = pDept.getRealZbCount();
			pDept.setRealCount(realPersonCount+addRealPersonCount);
			pDept.setRealCountAll(realCountAll+addRealPersonCountAll);
			pDept.setRealZbCount(realZbPersonCount+addRealZbPersonCount);
			Integer planCount = pDept.getPlanCount();
			Integer diffCount = pDept.getDiffCount();
			if(planCount==null){
				planCount = 0;
			}
			if(diffCount==null){
				diffCount = 0;
			}
			diffCount =  planCount - pDept.getRealZbCount();
			if(diffCount<0){
				diffCount = 0;
			}
			pDept.setDiffCount(diffCount);
			hrDepartmentCurrentDao.save(pDept);
			setDeptPersonCount(pDept,addRealPersonCount,addRealZbPersonCount,addRealPersonCountAll);
		}
	}
	
	@Override
	public void computePersonCount(String deptId) {
		//hrDepartmentCurrentDao.flusSession();
		TestTimer tt =new TestTimer("computePersonCountByDept");
		tt.begin();
		HrDepartmentCurrent hrDepartmentCurrent = hrDepartmentCurrentDao.get(deptId);
		List<PropertyFilter> personCountfilters = new ArrayList<PropertyFilter>();
		personCountfilters.add(new PropertyFilter("SQS_department.departmentId","dept_id='"+deptId+"'"));
		List<HrPersonCurrent> hrPersonCurrents = hrPersonCurrentDao.getByFilters(personCountfilters);
		int pc = hrPersonCurrents.size();
		personCountfilters.add(new PropertyFilter("EQB_disable","false"));
		hrPersonCurrents = hrPersonCurrentDao.getByFilters(personCountfilters);
		int pcNoDisabled = hrPersonCurrents.size();
		personCountfilters.add(new PropertyFilter("EQS_status.code","PT0101"));
		hrPersonCurrents = hrPersonCurrentDao.getByFilters(personCountfilters);
		int zbPcNoDisabled = hrPersonCurrents.size();
		int oldPcRealCount = hrDepartmentCurrent.getRealCount();
		int oldPcRealCountAll = hrDepartmentCurrent.getRealCountAll();
		int oldZbPcRealCount = hrDepartmentCurrent.getRealZbCount();
		int pcRealCountC = pcNoDisabled - oldPcRealCount;
		int pcRealCountAllC = pc - oldPcRealCountAll;
		int zbPcRealCountC = zbPcNoDisabled - oldZbPcRealCount;
		if(pcRealCountC==0&&zbPcRealCountC==0&&pcRealCountAllC==0){
			return ;
		}
		hrDepartmentCurrent.setRealCount(pcNoDisabled);
		hrDepartmentCurrent.setRealCountAll(pc);
		hrDepartmentCurrent.setRealZbCount(zbPcNoDisabled);
		Integer planCount = hrDepartmentCurrent.getPlanCount();
		Integer diffCount = hrDepartmentCurrent.getDiffCount();
		if(planCount==null){
			planCount = 0;
		}
		if(diffCount==null){
			diffCount = 0;
		}
		diffCount =  planCount - hrDepartmentCurrent.getRealZbCount();
		if(diffCount<0){
			diffCount = 0;
		}
		hrDepartmentCurrent.setDiffCount(diffCount);
		hrDepartmentCurrentDao.save(hrDepartmentCurrent);
		setDeptPersonCount(hrDepartmentCurrent,pcRealCountC,zbPcRealCountC,pc);
		tt.done();
		//hrDepartmentCurrentDao.flusSession();
	}

	/*@Override
	public void computePersonCount(HrPersonSnap personSnap) {
		HrDepartmentCurrent hrDepartmentCurrent = personSnap.getHrDept();
		hrDepartmentCurrent = hrDepartmentCurrentDao.get(hrDepartmentCurrent.getDepartmentId());
		int pcNoDisabled = hrDepartmentCurrent.getPersonCountWithoutDisabled();
		int zbPcNoDisabled = hrDepartmentCurrent.getZbPersonCountNotDisabled();
		System.out.println("部门："+hrDepartmentCurrent.getName()+"实有人数："+pcNoDisabled+";实有在编："+zbPcNoDisabled);
		hrDepartmentCurrent.setRealCount(pcNoDisabled);
		hrDepartmentCurrent.setRealZbCount(zbPcNoDisabled);
		Integer planCount = hrDepartmentCurrent.getPlanCount();
		Integer diffCount = hrDepartmentCurrent.getDiffCount();
		if(planCount==null){
			planCount = 0;
		}
		if(diffCount==null){
			diffCount = 0;
		}
		diffCount =  planCount - hrDepartmentCurrent.getRealZbCount();
		if(diffCount<0){
			diffCount = 0;
		}
		hrDepartmentCurrent.setDiffCount(diffCount);
		hrDepartmentCurrentDao.save(hrDepartmentCurrent);
		setDeptPersonCount(hrDepartmentCurrent,hrDepartmentCurrent.getPersonCountWithoutDisabled(),hrDepartmentCurrent.getZbPersonCountNotDisabled());
	}*/
	public void doSaveDepartMentSnap(String info) {    //{info的格式为  id,planCount:realCount}
		String[] hr_DepartMentSnap = info.split(",");
		if (hr_DepartMentSnap.length > 1) {   
			String id = hr_DepartMentSnap[0];
			String planC = hr_DepartMentSnap[1];
			String [] planAndReal = planC.split(":");
			Integer planCount = Integer.valueOf(planAndReal[0]);
			Integer realZbCount = Integer.valueOf(planAndReal[1]);
			HrDepartmentSnap snap = hrDepartmentSnapDao.get(id);
			Integer overCount = realZbCount-planCount;
			snap.setPlanCount(planCount);
		    if(planCount-realZbCount>0){
		    	snap.setDiffCount(planCount-realZbCount);
		    }else{
			    snap.setDiffCount(0);
		    }
		    if(overCount>0){
		    	snap.setOverCount(overCount);
		    }else{
		    	snap.setOverCount(0);
		    }
			hrDepartmentSnapDao.save(snap);
		}
	}
	@Override
	public Map<String, String> getDeptIdAndSnapCodeMap(){
		return hrDepartmentCurrentDao.getDeptIdAndSnapCodeMap();
	}
	@Override
	public List<Integer> getSUM(String string, List<PropertyFilter> filters) {
		return hrDepartmentCurrentDao.getSUM(string,filters);
	}
}