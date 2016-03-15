package com.huge.ihos.hr.pact.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.dao.PactDao;
import com.huge.ihos.hr.pact.dao.PactRelieveDao;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.model.PactRelieve;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.pact.service.PactRelieveManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("pactRelieveManager")
public class PactRelieveManagerImpl extends GenericManagerImpl<PactRelieve, String> implements PactRelieveManager {
    private PactRelieveDao pactRelieveDao;
    private PactDao pactDao;
    private BillNumberManager billNumberManager;
    private SysTableStructureManager sysTableStructureManager;
    private PactManager pactManager;
    private HrPersonSnapManager hrPersonSnapManager;
    
    
    @Autowired
    public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
 		this.sysTableStructureManager = sysTableStructureManager;
 	}
    
	@Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	@Autowired
    public void setPactDao(PactDao pactDao) {
		this.pactDao = pactDao;
	}

    @Autowired
    public PactRelieveManagerImpl(PactRelieveDao pactRelieveDao) {
        super(pactRelieveDao);
        this.pactRelieveDao = pactRelieveDao;
    }
    
    @Autowired
    public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}
    
    @Autowired
	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    public JQueryPager getPactRelieveCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return pactRelieveDao.getPactRelieveCriteria(paginatedList,filters);
	}

	@Override
	public void addPactRelieve(String pactIds, PactRelieve pactRelieve) {
		String[] pacts = pactIds.split(",");
		PactRelieve pr = null;
		Pact pact = null;
		for(String pactId:pacts){
			if("".equals(pactId)){
				continue;
			}
			pr = new PactRelieve();
			pact = pactDao.get(pactId);
			pr.setPact(pact);
			String relieveNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_RELIEVE,pactRelieve.getYearMonth());
			pr.setRelieveNo(relieveNo);
			pr.setRelieveDate(pactRelieve.getRelieveDate());
			pr.setRelieveReason(pactRelieve.getRelieveReason());
			pr.setRemark(pactRelieve.getRemark());
			pr.setState(pactRelieve.getState());
			pr.setMakeDate(pactRelieve.getMakeDate());
			pr.setMakePerson(pactRelieve.getMakePerson());
			pr.setYearMonth(pactRelieve.getYearMonth());
			super.save(pr);
			pact.setLockType(Pact.LOCK_RELIEVE);
			pactDao.save(pact);
			//上锁
			String lockState="JC";
			String personId=pact.getHrPerson().getPersonId();
			pactManager.lockPact(pactId, lockState);
			hrPersonSnapManager.lockHrPerson(personId, lockState);
		}
	}
	
	

	@Override
	public void confirmRelieve(String pactIds, PactRelieve pactRelieve,Date date, Person person) {
		HrPersonHis hrPersonHis = null;
		if(pactIds==null){
			pactRelieve.setConfirmDate(date);
			pactRelieve.setConfirmPerson(person);
			pactRelieve = this.save(pactRelieve);
			Pact pact = pactRelieve.getPact();
			pact.setSignState(5);
			pact.setRelieveDate(pactRelieve.getRelieveDate());
			pact.setRelieveReason(pactRelieve.getRelieveReason());
			pact.setLockType(Pact.LOCK_NO);
			pactDao.save(pact);
			hrPersonHis = pact.getHrPersonHis();
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "解除合同");
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("pactNo", pact.getCode());
			String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date);
			hrSubDataMap.put("changeDate", changeDate);
			hrSubDataMap.put("remark", "合同被解除");
			sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			//解锁
			String pactId=pact.getId();
			String personId=pact.getHrPerson().getPersonId();
			String lockState="JC";
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactRelieve> pactRelievesTemp=pactRelieveDao.getByFilters(filters);
			if(pactRelievesTemp==null||pactRelievesTemp.size()==0){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactRelievesTemp=pactRelieveDao.getByFilters(filters);
			if(pactRelievesTemp==null||pactRelievesTemp.size()==0){
				pactManager.unlockPact(pactId, lockState);
			}
		}else{
			String[] pacts = pactIds.split(",");
			PactRelieve pr = null;
			Pact pact = null;
			for(String pactId:pacts){
				if("".equals(pactId)){
					continue;
				}
				pr = new PactRelieve();
				pact = pactDao.get(pactId);
				pr.setPact(pact);
				String relieveNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_RELIEVE,pactRelieve.getYearMonth());
				pr.setRelieveNo(relieveNo);
				pr.setRelieveDate(pactRelieve.getRelieveDate());
				pr.setRelieveReason(pactRelieve.getRelieveReason());
				pr.setRemark(pactRelieve.getRemark());
				pr.setState(pactRelieve.getState());
				pr.setMakeDate(pactRelieve.getMakeDate());
				pr.setMakePerson(pactRelieve.getMakePerson());
				pr.setCheckDate(pactRelieve.getCheckDate());
				pr.setCheckPerson(pactRelieve.getCheckPerson());
				pr.setConfirmDate(date);
				pr.setConfirmPerson(person);
				pr.setYearMonth(pactRelieve.getYearMonth());
				super.save(pr);
				
				pact.setSignState(5);
				pact.setRelieveDate(pactRelieve.getRelieveDate());
				pact.setRelieveReason(pactRelieve.getRelieveReason());
				pactDao.save(pact);
				hrPersonHis = pact.getHrPersonHis();
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changeType", "解除合同");
				hrSubDataMap.put("changePerson", person.getName());
				hrSubDataMap.put("pactNo", pact.getCode());
				String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date);
				hrSubDataMap.put("changeDate", changeDate);
				hrSubDataMap.put("remark", "合同被解除");
				sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			
			}
		}
	}

	@Override
	public void remove(String[] ids) {
		Pact pact = null;
		for(String id:ids){
			pact = this.pactRelieveDao.get(id).getPact();
			pact.setLockType(Pact.LOCK_NO);
			this.pactDao.save(pact);
			//解锁
			String pactId=pact.getId();
			String personId=pact.getHrPerson().getPersonId();
			String lockState="JC";
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactRelieve> pactRelievesTemp=pactRelieveDao.getByFilters(filters);
			if(pactRelievesTemp!=null&&pactRelievesTemp.size()==1){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactRelievesTemp=pactRelieveDao.getByFilters(filters);
			if(pactRelievesTemp!=null&&pactRelievesTemp.size()==1){
				pactManager.unlockPact(pactId, lockState);
			}
		}
		super.remove(ids);
	}
}