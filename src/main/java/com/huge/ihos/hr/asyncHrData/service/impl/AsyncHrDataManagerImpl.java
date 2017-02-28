package com.huge.ihos.hr.asyncHrData.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.asyncHrData.service.AsyncHrDataManager;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;
@Service("asyncHrDataManager")
public class AsyncHrDataManagerImpl implements AsyncHrDataManager{
	private HrOrgSnapManager hrOrgSnapManager;
	private HrDepartmentSnapManager hrDepartmentSnapManager;
//	private HrDepartmentSnapDao hrDepartmentSnapDao;
	private HrPersonSnapManager hrPersonSnapManager;
	private OrgManager orgManager;
	private DepartmentManager departmentManager;
	private PersonManager personManager;
	private HrOperLogManager hrOperLogManager;
	
//	@Autowired
//	public void setHrDepartmentSnapDao(HrDepartmentSnapDao hrDepartmentSnapDao) {
//		this.hrDepartmentSnapDao = hrDepartmentSnapDao;
//	}
	@Autowired
	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}
	
	@Autowired
	public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
	
	@Autowired
	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
	@Autowired
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	@Autowired
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	@Autowired
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
	@Autowired
	public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}
	@Override
	public void asyncHrData(String timestamp) {
		//asyncOrgData(timestamp);
		//asyncDeptData(timestamp);
		//asyncPersonData(timestamp);
		//TestTimer tt2 = new TestTimer("数据库");
		//tt2.begin();
		orgManager.disableOrgAfterSync(timestamp);
		departmentManager.disableOrgAfterSync(timestamp);
		personManager.disableOrgAfterSync(timestamp);
		//tt2.done();
		/*单位*/
		//TestTimer tt = new TestTimer("单位数据准备");
		//tt.begin();
		List<HrOrgSnap> orgSnapList = hrOrgSnapManager.getOrgListByHisTime(timestamp);
		List<Org> orgList = new ArrayList<Org>();
		if(orgSnapList!=null && !orgSnapList.isEmpty()){
			for(HrOrgSnap hrOrgSnap:orgSnapList){
				String orgCode = hrOrgSnap.getOrgCode();
				//Org org = null;
				//if(orgManager.exists(orgCode)){
				//Org	org = orgManager.get(orgCode);
				//}else{
				Org org = new Org();
				org.setOrgCode(orgCode);
				//}
				org.setOrgname(hrOrgSnap.getOrgname());
				org.setAddress(hrOrgSnap.getAddress());
				org.setContact(hrOrgSnap.getContact());
				org.setContactPhone(hrOrgSnap.getContactPhone());
				org.setDisabled(hrOrgSnap.getDisabled());
				org.setEmail(hrOrgSnap.getEmail());
				org.setFax(hrOrgSnap.getFax());
				org.setHomePage(hrOrgSnap.getHomePage());
				org.setInternal(hrOrgSnap.getInternal());
				org.setInvalidDate(hrOrgSnap.getInvalidDate());
				org.setNote(hrOrgSnap.getNote());
				org.setOwnerOrg(hrOrgSnap.getOwnerOrg());
				if(hrOrgSnap.getParentOrg() != null){
					org.setParentOrgCode(new Org());
					org.getParentOrgCode().setOrgCode(hrOrgSnap.getParentOrgCode());
				}
				org.setPhone(hrOrgSnap.getPhone());
				org.setType(hrOrgSnap.getType());
				org.setShortName(hrOrgSnap.getShortName());
				org.setPostCode(hrOrgSnap.getPostCode());
				org.setSnapCode(hrOrgSnap.getSnapCode());
				orgList.add(org);
			}
		}
		//tt.done();
		//tt = new TestTimer("部门数据准备");
		//tt.begin();
		/*单位*/
		List<HrDepartmentSnap> deptSnapList = hrDepartmentSnapManager.getDeptListByHisTime(timestamp);
		List<Department> deptList = new ArrayList<Department>();
		if(deptSnapList!=null && !deptSnapList.isEmpty()){
			for(HrDepartmentSnap deptSnap:deptSnapList){
				String deptId=deptSnap.getDeptId();
				//Department dept = null;
				//if(departmentManager.exists(deptId)){
				//	dept = departmentManager.get(deptId);
				//}else{
				Department	dept = new Department();
				dept.setDepartmentId(deptId);
				//}
				dept.setName(deptSnap.getName());
				dept.setDeptCode(deptSnap.getDeptCode());
				dept.setShortnName(deptSnap.getShortName());
				dept.setInternalCode(deptSnap.getInternalCode());
				dept.setOutin(deptSnap.getAttrCode());
				dept.setDeptClass(deptSnap.getDeptType());
				dept.setSubClass(deptSnap.getSubClass());
				dept.setPhone(deptSnap.getPhone());
				dept.setContact(deptSnap.getContact());
				dept.setContactPhone(deptSnap.getContactPhone());
				dept.setManager(deptSnap.getManager());
				dept.setNote(deptSnap.getRemark());
				dept.setLeaf(deptSnap.getLeaf());
				dept.setClevel(deptSnap.getClevel());
				if(OtherUtil.measureNotNull(deptSnap.getParentDeptId())){
					dept.setParentDept(new Department());
					dept.getParentDept().setDepartmentId(deptSnap.getParentDeptId());
				}
				dept.setDisabled(deptSnap.getDisabled());
				dept.setInvalidDate(deptSnap.getInvalidDate());
				dept.setCnCode(deptSnap.getCnCode());
				if(OtherUtil.measureNotNull(deptSnap.getJjDeptId())){
					dept.setJjDept(new Department());
					dept.getJjDept().setDepartmentId(deptSnap.getJjDeptId());
				}
				dept.setJjLeaf(deptSnap.getJjLeaf());
				if(OtherUtil.measureNotNull(deptSnap.getYsDeptId())){
					dept.setYsDept(new Department());
					dept.getYsDept().setDepartmentId(deptSnap.getYsDeptId());
				}
				dept.setYsLeaf(deptSnap.getYsLeaf());
				dept.setJjDeptType(deptSnap.getJjDeptType());
				dept.setBranchCode(deptSnap.getBranchCode());
				dept.setDgroup(deptSnap.getDgroup());
				dept.setXmLeaf(deptSnap.getXmLeaf());
				dept.setCbLeaf(deptSnap.getCbLeaf());
				dept.setCrLeaf(deptSnap.getCrLeaf());
				dept.setZcLeaf(deptSnap.getZcLeaf());
				dept.setOrgCode(deptSnap.getOrgCode());
				dept.setSnapCode(deptSnap.getSnapCode());
				deptList.add(dept);
			}
		}
		//tt.done();
		//tt = new TestTimer("人员数据准备");
		//tt.begin();
		/*人*/
		List<HrPersonSnap> personSnapList = hrPersonSnapManager.getPersonListByHisTime(timestamp);
		List<Person> personList = new ArrayList<Person>();
		if(personSnapList!=null && !personSnapList.isEmpty()){
			for(HrPersonSnap hrPersonSnap:personSnapList){
				String personId = hrPersonSnap.getPersonId();
				//Person person = null;
				//if(personManager.exists(personId)){
				//	person = personManager.get(personId);
				//}else{
				Person	person = new Person();
				person.setPersonId(personId);
				//}	
				person.setName(hrPersonSnap.getName());
				person.setPersonCode(hrPersonSnap.getPersonCode());
				person.setSex(hrPersonSnap.getSex());
				person.setStatus(hrPersonSnap.getEmpType().getName());
				if(OtherUtil.measureNotNull(hrPersonSnap.getDuty())&&OtherUtil.measureNotNull(hrPersonSnap.getDuty().getName())){
					person.setDuty(hrPersonSnap.getDuty().getName());
				}
				person.setBirthday(hrPersonSnap.getBirthday());
				person.setPostType(hrPersonSnap.getPostType());
				person.setEducationalLevel(hrPersonSnap.getEducationalLevel());
				person.setDisable(hrPersonSnap.getDisabled());
				person.setIdNumber(hrPersonSnap.getIdNumber());
				person.setJobTitle(hrPersonSnap.getJobTitle());
				person.setNote(hrPersonSnap.getRemark());
				person.setRatio(hrPersonSnap.getRatio());
				person.setSalaryNumber(hrPersonSnap.getSalaryNumber());
				person.setWorkBegin(hrPersonSnap.getWorkBegin());
				person.setDepartment(new Department());
				person.getDepartment().setDepartmentId(hrPersonSnap.getHrDept().getDepartmentId());
				person.setJjmark(hrPersonSnap.getJjmark());
				person.setOrgCode(hrPersonSnap.getOrgCode());
				person.setSnapCode(hrPersonSnap.getSnapCode());
				person.setBranchCode(hrPersonSnap.getBranchCode());
				
				person.setGzType(hrPersonSnap.getGzType());
				person.setGzType2(hrPersonSnap.getGzType2());
				personList.add(person);
			}
		}
		//tt.done();
		//TestTimer tt1 = new TestTimer("保存");
		//tt1.begin();
		orgManager.saveAll(orgList);
		departmentManager.saveAll(deptList);
		personManager.saveAll(personList);
		//tt1.done();
	}
	@Override
	public void asyncToHrData(List<String> idl,String orgCode,String orgName,Boolean isHrOrg,String deptIds,Person person,Date date){
		String snapCode=DateUtil.getSnapCode();
		if(!isHrOrg){
			 hrOrgSnapManager.syncUpdateHrOrg(orgCode, orgName, snapCode,person,date);
		}
		asyncHrDeptData(idl,orgCode,snapCode,person,date);
		asyncHrPersonData(deptIds,snapCode,person,date);
	}
	private void asyncPersonData(String timestamp) {
		List<HrPersonSnap> personSnapList = hrPersonSnapManager.getPersonListByHisTime(timestamp);
		if(personSnapList!=null && !personSnapList.isEmpty()){
			for(HrPersonSnap personSnap:personSnapList){
				hrPersonSnapManager.snycUpdatePerson(personSnap, HrOperLog.OPER_EDIT);
			}
		}
		personManager.disableOrgAfterSync(timestamp);
	}

	private void asyncDeptData(String timestamp) {
		List<HrDepartmentSnap> deptSnapList = hrDepartmentSnapManager.getDeptListByHisTime(timestamp);
		if(deptSnapList!=null && !deptSnapList.isEmpty()){
			for(HrDepartmentSnap deptSnap:deptSnapList){
				hrDepartmentSnapManager.syncUpdateDepartment(deptSnap, HrOperLog.OPER_EDIT);
			}
		}
		departmentManager.disableOrgAfterSync(timestamp);
	}

	private void asyncOrgData(String timestamp) {
		List<HrOrgSnap> orgSnapList = hrOrgSnapManager.getOrgListByHisTime(timestamp);
		if(orgSnapList!=null && !orgSnapList.isEmpty()){
			for(HrOrgSnap orgSnap:orgSnapList){
				hrOrgSnapManager.syncUpdateOrg(orgSnap, HrOperLog.OPER_EDIT);
			} 
		}
		orgManager.disableOrgAfterSync(timestamp);
	}
	private void asyncHrDeptData(List<String> idl,String orgCode,String snapCode,Person person,Date date){
		for(String deptId:idl){
			 hrDepartmentSnapManager.syncUpdateHrDepartment(orgCode, deptId, snapCode,person,date);
		}
	}
	private void asyncHrPersonData(String deptIds,String snapCode,Person person,Date date){
		List<Person> personList=new ArrayList<Person>();
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
		filters.add(new PropertyFilter("EQS_snapCode","0"));
		personList=personManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(personList)&&!personList.isEmpty()){
			for(Person personTemp:personList){
				hrPersonSnapManager.syncUpdateHrPerson(personTemp, snapCode,person,date);
			}
		}
	}
	@Override
	public void asyncToHr(List<HrDepartmentSnap> hdss,List<Department> depts,List<HrLogEntityInfo<HrDepartmentSnap>> entityDeptInfos,List<HrPersonSnap> hpss,List<Person> personList,List<HrLogEntityInfo<HrPersonSnap>> entityPersonInfos,Person operPerson,Date operDate){
//		TestTimer deptSave = new TestTimer("deptSave");
//		deptSave.begin();
		hrDepartmentSnapManager.saveAll(hdss);
//		String updateDepartment = "update t_department set snapCode='"+snapCode+"' where deptId in "+deptIds;
//		deptSave.done();
//		deptSave = new TestTimer("oldDeptUpdate");
//		deptSave.begin();
//		hrDepartmentSnapDao.excuteSql(updateDepartment);
		departmentManager.saveAll(depts);
//		deptSave.done();
//		TestTimer personSave = new TestTimer("personSave");
//		personSave.begin();
		hrPersonSnapManager.saveAll(hpss);
//		int hpssSize = hpss.size() , i=50 ,end = 0;
//		while(i<=hpssSize&&end==0){
//			List hrPersonList = hpss.subList(i-50, i-1);
//			hrPersonSnapManager.statelessSaveAll(hrPersonList);
//			i += 50;
//			if(i>hpssSize){
//				i = hpssSize;
//				end=1;
//			}
//		}
//		deptSave.done();
//		personSave = new TestTimer("oldPersonUpdate");
//		personSave.begin();
//		String updatePerson = "update t_person set snapCode='"+snapCode+"' where personId in "+personIds;
//		hrDepartmentSnapDao.excuteSql(updatePerson);
		personManager.saveAll(personList);
//		personSave.done();
//		TestTimer logSave = new TestTimer("logSave");
//		logSave.begin();
		if(OtherUtil.measureNotNull(entityDeptInfos)&&!entityDeptInfos.isEmpty()){
			for(HrLogEntityInfo<HrDepartmentSnap> entityDeptInfo:entityDeptInfos){
				hrOperLogManager.addHrOperLog(entityDeptInfo, operPerson, operDate);
			}
		}
		if(OtherUtil.measureNotNull(entityPersonInfos)&&!entityPersonInfos.isEmpty()){
			for(HrLogEntityInfo<HrPersonSnap> entityPersonInfo:entityPersonInfos){
				hrOperLogManager.addHrOperLog(entityPersonInfo, operPerson, operDate);
			}
		}
//		logSave.done();
	}
}
