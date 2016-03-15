package com.huge.ihos.hr.changesInPersonnel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.changesInPersonnel.dao.SysPersonLeaveDao;
import com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave;
import com.huge.ihos.hr.changesInPersonnel.service.SysPersonLeaveManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.model.PactRelieve;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.pact.service.PactRelieveManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("sysPersonLeaveManager")
public class SysPersonLeaveManagerImpl extends GenericManagerImpl<SysPersonLeave, String> implements SysPersonLeaveManager {
    private SysPersonLeaveDao sysPersonLeaveDao;
    private HrPersonSnapManager hrPersonSnapManager;
    private PersonTypeManager personTypeManager;
    private PactManager pactManager;
    private PactRelieveManager pactRelieveManager;
    private BillNumberManager billNumberManager;
    private SysTableStructureManager sysTableStructureManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    
    @Autowired
    public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	@Autowired
     public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
 		this.sysTableStructureManager = sysTableStructureManager;
 	}
    @Autowired
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
    @Autowired
    public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}
    @Autowired
    public void setPactRelieveManager(PactRelieveManager pactRelieveManager) {
		this.pactRelieveManager = pactRelieveManager;
	}
    @Autowired
    public void setPactManager(PactManager pactManager) {
  		this.pactManager = pactManager;
  	}
	@Autowired
    public SysPersonLeaveManagerImpl(SysPersonLeaveDao sysPersonLeaveDao) {
        super(sysPersonLeaveDao);
        this.sysPersonLeaveDao = sysPersonLeaveDao;
    }
	@Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

    @Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    @Autowired
    public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
    public JQueryPager getSysPersonLeaveCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysPersonLeaveDao.getSysPersonLeaveCriteria(paginatedList,filters);
	}
    
    @Override
    public void auditSysPersonLeave(List<String> checkIds,Person person,Date date){
    	SysPersonLeave sysPersonLeave = null;
		for(String checkId:checkIds){
			sysPersonLeave = sysPersonLeaveDao.get(checkId);
			sysPersonLeave.setState("1");
			sysPersonLeave.setChecker(person);
			sysPersonLeave.setCheckDate(date);
			sysPersonLeaveDao.save(sysPersonLeave);
		}
    }
    
    @Override
    public void antiSysPersonLeave(List<String> cancelCheckIds){
    	SysPersonLeave sysPersonLeave = null;
		for(String cancelCheckId:cancelCheckIds){
			sysPersonLeave = sysPersonLeaveDao.get(cancelCheckId);
			sysPersonLeave.setState("0");
			sysPersonLeave.setChecker(null);
			sysPersonLeave.setCheckDate(null);
			sysPersonLeaveDao.save(sysPersonLeave);	
		}
    }
    
    @Override
    public void doneSysPersonLeave(List<String> doneIds,Person person,Date date,boolean ansycData){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_name","离职"));
    	List<PersonType> hrPersonTypeList = personTypeManager.getByFilters(filters);
    	PersonType personType=null;
    	if(hrPersonTypeList==null||hrPersonTypeList.isEmpty()){
    		personType=new PersonType();
    		personType.setCode("PT0201");
    		personType.setName("离职");
    		personType.setMakeDate(date);
    		personType.setMaker(person);
    		personType.setSn(0);
    		personType.setRemark("人员离职生成");
    		personType = personTypeManager.save(personType);
    	}else{
    		personType= hrPersonTypeList.get(0);
    	}
    	SysPersonLeave sysPersonLeave = null;
    	HrPersonHis hrPersonHis = null;
    	HrPersonSnap leavePerson = null;
    	for(String doneId:doneIds){
    		sysPersonLeave = sysPersonLeaveDao.get(doneId);
    		hrPersonHis = sysPersonLeave.getHrPersonHis();
    		leavePerson = hrPersonSnapManager.get(hrPersonHis.getHisSnapId()).clone();
    		leavePerson.setEmpType(personType);
    		leavePerson.setDisabled(true);
    		HrPersonSnap hrPersonSnap = hrPersonSnapManager.saveHrPerson(leavePerson, null,person, date,ansycData);
    	
    		sysPersonLeave.setState("2");
    		sysPersonLeave.setDoner(person);
			sysPersonLeave.setDoneDate(date);
			sysPersonLeave.setChangedSnapCode(hrPersonSnap.getSnapCode());
    		sysPersonLeaveDao.save(sysPersonLeave);
    		
    		/**子集写记录**/
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "人员离职");
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeContent", hrPersonSnap.getName()+"离职");
			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
			
			//解锁
			String personId=sysPersonLeave.getHrPerson().getPersonId();
			String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
			unlockSysPersonLeave(personId, deptId);
    	}
    }
    @Override
    public SysPersonLeave saveAndDoneSysPersonLeave(SysPersonLeave sysPersonLeave,Person person,Date date,Boolean isEntityIsNew,boolean ansycData){
    	if(isEntityIsNew){
    		sysPersonLeave.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_LEAVE,sysPersonLeave.getYearMonth()));
//			sysPersonLeave.setCode(billNumberManager.createNextBillNumber(BillNumberConstants.SUBSYSTEM_HR, BillNumberConstants.HR_PERSON_LEAVE, BillNumberConstants.SUBSYSTEM_HR, BillNumberConstants.SUBSYSTEM_HR));
		}
    	sysPersonLeave=this.save(sysPersonLeave);
    	String doneId=sysPersonLeave.getId();
		this.doneSysPersonLeaveOnlyOne(doneId,person,date,ansycData);
		return sysPersonLeave;
    }
    @Override
    public void doneChangePact(List<String> doneIds,Person person,Date date,String yearMonth){
    	SysPersonLeave sysPersonLeave = null;
		for(String doneId:doneIds){
			sysPersonLeave = sysPersonLeaveDao.get(doneId);
			String personId=sysPersonLeave.getHrPerson().getPersonId();
    		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
    		List<Pact> pacts=new ArrayList<Pact>();
    		pacts=pactManager.getByFilters(filters);
    		if(pacts!=null&&pacts.size()>0){
    			for(Pact pact:pacts){
    				PactRelieve pactRelieve=new PactRelieve();
    				pactRelieve.setCheckDate(date);
    				pactRelieve.setCheckPerson(person);
    				pactRelieve.setMakeDate(date);
    				pactRelieve.setMakePerson(person);
    				pactRelieve.setPact(pact);
    				pactRelieve.setRelieveDate(date);
    				pactRelieve.setConfirmDate(date);
    				pactRelieve.setConfirmPerson(person);
    				pactRelieve.setRelieveReason("人员信息管理人员离职变动");
    				pactRelieve.setState(3);
    				String relieveNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_RELIEVE,yearMonth);
    				pactRelieve.setRelieveNo(relieveNo);
    				pactRelieve.setYearMonth(yearMonth);
    				pactRelieveManager.save(pactRelieve);
    				pact.setRelieveDate(date);
    				pact.setRelieveReason("人员信息管理人员离职变动");
    				pact.setSignState(5);
    				pactManager.save(pact);
    			}
    		}
		}
    }
    private void doneSysPersonLeaveOnlyOne(String doneId,Person person,Date date,boolean ansycData){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_code","PT0201"));
    	List<PersonType> personTypeList = personTypeManager.getByFilters(filters);
    	PersonType personType=null;
    	if(personTypeList==null||personTypeList.isEmpty()){
    		personType=new PersonType();
    		personType.setCode("PT0201");
    		personType.setName("离职");
    		personType.setMakeDate(date);
    		personType.setMaker(person);
    		personType.setSn(0);
    		personType.setRemark("人员离职生成");
    		personType = personTypeManager.save(personType);
    	}else{
    		personType = personTypeList.get(0);
    	}
    	SysPersonLeave sysPersonLeave = null;
    	HrPersonHis hrPersonHis = null;
    	HrPersonSnap leavePerson = null;
    		sysPersonLeave = sysPersonLeaveDao.get(doneId);
    		hrPersonHis = sysPersonLeave.getHrPersonHis();
    		leavePerson = hrPersonSnapManager.get(hrPersonHis.getHisSnapId()).clone();
    		leavePerson.setEmpType(personType);
    		leavePerson.setDisabled(true);
    		HrPersonSnap hrPersonSnap = hrPersonSnapManager.saveHrPerson(leavePerson, null,person, date,ansycData);
    		
    		sysPersonLeave.setCheckDate(date);
    		sysPersonLeave.setChecker(person);
    		sysPersonLeave.setState("2");
    		sysPersonLeave.setDoner(person);
			sysPersonLeave.setDoneDate(date);
			sysPersonLeave.setChangedSnapCode(hrPersonSnap.getSnapCode());
    		sysPersonLeaveDao.save(sysPersonLeave);
    		
    		/**子集写记录**/
			Map<String,String> hrSubDataMap = new HashMap<String, String>();
			hrSubDataMap.put("changeType", "人员离职");
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeContent", hrPersonSnap.getName()+"离职");
			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
    }
    @Override
    public SysPersonLeave saveSysPersonLeave(SysPersonLeave sysPersonLeave,Boolean isEntityIsNew){
    	if(isEntityIsNew){
    		sysPersonLeave.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_LEAVE,sysPersonLeave.getYearMonth()));
    		lockSysPersonLeave(sysPersonLeave);
    	}
    	sysPersonLeave=sysPersonLeaveDao.save(sysPersonLeave);
    	return sysPersonLeave;
    }
    private void lockSysPersonLeave(SysPersonLeave sysPersonLeave){
    	String personId=sysPersonLeave.getHrPerson().getPersonId();
		String lockState="LZ";
		hrPersonSnapManager.lockHrPerson(personId, lockState);
		String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
		hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, lockState);
    }
    private void unlockSysPersonLeave(String personId,String deptId){
    	String lockState="LZ";
    	List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_hrPerson.personId",personId));
    	filters.add(new PropertyFilter("NES_state","2"));
    	List<SysPersonLeave> sysPersonLeavesTemp=sysPersonLeaveDao.getByFilters(filters);
    	if(sysPersonLeavesTemp==null||sysPersonLeavesTemp.isEmpty()){
    		hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
    	}
    	filters.clear();
	    filters.add(new PropertyFilter("EQS_hrPerson.department.departmentId",deptId));
	    filters.add(new PropertyFilter("NES_state","2"));
	    if(sysPersonLeavesTemp==null||sysPersonLeavesTemp.isEmpty()){
    		hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, lockState);
    	}
    }
    @Override
    public void deleteSysPersonLeave(List<String> delIds){
    	SysPersonLeave sysPersonLeave=null;
    	for(String delId:delIds){
    		sysPersonLeave=sysPersonLeaveDao.get(delId);
    		String personId=sysPersonLeave.getHrPerson().getPersonId();
    		String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
		    sysPersonLeaveDao.remove(delId);
		    unlockSysPersonLeave(personId,deptId);
    	}
    }
}