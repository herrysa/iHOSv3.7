package com.huge.ihos.hr.pact.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.dao.PactDao;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("pactManager")
public class PactManagerImpl extends GenericManagerImpl<Pact, String> implements PactManager {
    private PactDao pactDao;
    private BillNumberManager billNumberManager;
    private HrPersonSnapManager hrPersonSnapManager;
    private PersonTypeManager personTypeManager;
    private SysTableStructureManager sysTableStructureManager;
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
    public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}
    
    @Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    
	@Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

    @Autowired
    public PactManagerImpl(PactDao pactDao) {
        super(pactDao);
        this.pactDao = pactDao;
    }
    
    public JQueryPager getPactCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return pactDao.getPactCriteria(paginatedList,filters);
	}

    @Override
	public Pact savePact(Pact pact,Person person){
		if(OtherUtil.measureNull(pact.getId())){
			String code = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_NEW,pact.getYearMonth());
			pact.setCode(code);
		}
		String lockState="HT";
		String personId=pact.getHrPerson().getPersonId();
		if(pact.getSignState()==3){
			// 向人员的合同子集写入一条添加合同的记录
			HrPersonHis hrPersonHis = pact.getHrPersonHis();
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeType", "签订合同");
			hrSubDataMap.put("pactNo", pact.getCode());
			String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", pact.getConfirmDate());
			hrSubDataMap.put("changeDate", changeDate);
			hrSubDataMap.put("remark", "新签订的合同");
			sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			//解锁
			pact.setLockState(null);
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_signState", "1,2"));
			List<Pact> pactsTemp=pactDao.getByFilters(filters);
			if(pactsTemp==null||pactsTemp.size()==0){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			
		}else{
			//上锁
			hrPersonSnapManager.lockHrPerson(personId, lockState);
			pact.setLockState(lockState);
		}
		return pactDao.save(pact);
	}
	@Override
	public void deletePact(List<String> delIds){
		Pact pact=null;
		for(String delId:delIds){
			pact=pactDao.get(delId);
			String lockState="HT";
			String personId=pact.getHrPerson().getPersonId();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_signState", "1,2"));
			List<Pact> pactsTemp=pactDao.getByFilters(filters);
			if(pactsTemp!=null&&pactsTemp.size()==1){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			pactDao.remove(delId);
		}
	}
	@Override
	public void updatePersonByPactChange(List<String> pactIdList, Date date,Person person,String changeType,boolean asyncData) {
		HrPersonCurrent hrPerson = null;
		HrPersonSnap hrPersonSnap = null;
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
		for(String id:pactIdList){
			hrPerson = this.get(id).getHrPerson();
			hrPersonSnap = hrPersonSnapManager.get(hrPerson.getPersonId()+"_"+hrPerson.getSnapCode()).clone();
			hrPersonSnap.setEmpType(personType);
			hrPersonSnap.setDisabled(true);
			hrPersonSnapManager.saveHrPerson(hrPersonSnap, null, person, date,asyncData);
			/**人事异动子集写记录**/
//			Map<String,String> hrSubDataMap=new HashMap<String, String>();
//			hrSubDataMap.put("changeType", changeType);
//			String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date);
//			hrSubDataMap.put("changeDate", changeDate);
//			hrSubDataMap.put("changeContent", changeType+"引起的离职");
//			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
		}
	}
	 @Override
	 public void lockPact(String pactId,String lockState){
		 Pact pact=null;
		 pact=pactDao.get(pactId);
		 String originalLockState=pact.getLockState();
			String nowLockState=null;
			if(originalLockState==null||originalLockState.equals("")){
				nowLockState=lockState;
			}else{
				String[] states=originalLockState.split(",");
				List<String> statelist = new ArrayList<String>();
				statelist = Arrays.asList(states);   
				if(statelist.contains(lockState)){
					nowLockState=originalLockState;
				}else{
					nowLockState=originalLockState+","+lockState;
				}
			}
			pact.setLockState(nowLockState);
			pactDao.save(pact);
	 }
	 @Override
	 public void unlockPact(String pactId,String lockState){
		 Pact pact=null;
		 pact=pactDao.get(pactId);
		 String originalLockState=pact.getLockState();
		 String nowLockState="";
		 if(OtherUtil.measureNotNull(originalLockState)){
			 String[] states=originalLockState.split(",");
			 String stateTem = null;
			 for(int stateIndx=0; stateIndx < states.length; stateIndx++){
				 stateTem=states[stateIndx];
				 if(stateTem.equals(lockState)){
					 continue;
				 }else{
					 nowLockState += stateTem +",";
				 }
			 }
			 if(!nowLockState.equals("")){
				 nowLockState = OtherUtil.subStrEnd(nowLockState, ",");
			 }
		 }
		 pact.setLockState(nowLockState);
		 pactDao.save(pact);
	 }
	 @Override
	 public String checkLockPact(String pactId,String[] checkLockStates){
		 Pact pact=null;
		 pact=pactDao.get(pactId);
		 String messtr=null;
		 String lockState=pact.getLockState();
			if(lockState==null||lockState.equals("")){
			}else{
				String[] states=lockState.split(",");
				List<String> checkStatelist = new ArrayList<String>();
				checkStatelist = Arrays.asList(checkLockStates);
				for(int stateIndex=0;stateIndex<states.length;stateIndex++){
					String stateTemp=states[stateIndex];
					if(checkStatelist.contains(stateTemp)){
						if(messtr==null){
							messtr=stateTemp;
						}else{
							messtr+=","+stateTemp;
						}
					}
				}
			}
			return messtr;
	 }
}