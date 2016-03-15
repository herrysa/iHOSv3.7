package com.huge.ihos.hr.pact.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.dao.PactDao;
import com.huge.ihos.hr.pact.dao.PactRenewDao;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.model.PactRenew;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.pact.service.PactRenewManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("pactRenewManager")
public class PactRenewManagerImpl extends GenericManagerImpl<PactRenew, String> implements PactRenewManager {
    private PactRenewDao pactRenewDao;
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
    public PactRenewManagerImpl(PactRenewDao pactRenewDao) {
        super(pactRenewDao);
        this.pactRenewDao = pactRenewDao;
    }
	@Autowired
	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}
	@Autowired
	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    public JQueryPager getPactRenewCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return pactRenewDao.getPactRenewCriteria(paginatedList,filters);
	}

	@Override
	public void addPactRenew(String pactIds, PactRenew pactRenew) {
		String[] pacts = pactIds.split(",");
		PactRenew pr = null;
		Pact pact = null;
		for(String pactId:pacts){
			if("".equals(pactId)){
				continue;
			}
			pr = new PactRenew();
			pact = pactDao.get(pactId);
			pr.setPact(pact);
			pr.setRemark(pactRenew.getRemark());
			String renewNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_RENEW,pactRenew.getYearMonth());
			pr.setRenewNo(renewNo);
			pr.setRenewYear(pactRenew.getRenewYear());
			pr.setState(pactRenew.getState());
			pr.setValidDate(pactRenew.getValidDate());
			pr.setMakeDate(pactRenew.getMakeDate());
			pr.setMakePerson(pactRenew.getMakePerson());
			pr.setYearMonth(pactRenew.getYearMonth());
			super.save(pr);
			pact.setLockType(Pact.LOCK_RENEW);
			pactDao.save(pact);
			//上锁
			String lockState="XQ";
			pactManager.lockPact(pactId, lockState);
			String personId=pact.getHrPerson().getPersonId();
			hrPersonSnapManager.lockHrPerson(personId, lockState);
		}
	}
	
	@Override
	public void confirmRenew(String pactIds,PactRenew pactRenew, Date operTime, Person person) {
		HrPersonHis hrPersonHis = null;
		if(pactIds==null){
			pactRenew.setConfirmDate(operTime);
			pactRenew.setConfirmPerson(person);
			pactRenew = this.save(pactRenew);
			Pact pact = pactRenew.getPact();
			pact.setPactState(2);
			pact.setSignTimes(pact.getSignTimes()+1);
			pact.setSignYear(pactRenew.getRenewYear());
			Calendar cal = Calendar.getInstance();
			cal.setTime(pact.getEndDate());
			cal.add(Calendar.YEAR, pactRenew.getRenewYear());
			pact.setCompSignDate(operTime);
			pact.setCompSignPeople(person.getName());
			pact.setBeginDate(pact.getEndDate());
			pact.setEndDate(cal.getTime());
			pact.setLockType(Pact.LOCK_NO);
			pactDao.save(pact);
			// 在人员的合同子集处增加续签记录
			hrPersonHis = pact.getHrPersonHis();
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "续签合同");
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("pactNo", pact.getCode());
			String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", operTime);
			hrSubDataMap.put("changeDate", changeDate);
			hrSubDataMap.put("remark", "合同被续签");
			sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			//解锁
			String lockState="XQ";
			String personId=pact.getHrPerson().getPersonId();
			String pactId=pact.getId();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactRenew> pactRenewsTemp=pactRenewDao.getByFilters(filters);
			if(pactRenewsTemp==null||pactRenewsTemp.size()==0){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactRenewsTemp=pactRenewDao.getByFilters(filters);
			if(pactRenewsTemp==null||pactRenewsTemp.size()==0){
				pactManager.unlockPact(pactId, lockState);
			}
		}else{
			String[] pacts = pactIds.split(",");
			PactRenew pr = null;
			Pact pact = null;
			for(String pactId:pacts){
				if("".equals(pactId)){
					continue;
				}
				pr = new PactRenew();
				pact = pactDao.get(pactId);
				pr.setPact(pact);
				pr.setRemark(pactRenew.getRemark());
				String renewNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_RENEW,pactRenew.getYearMonth());
				pr.setRenewNo(renewNo);
				pr.setRenewYear(pactRenew.getRenewYear());
				pr.setState(pactRenew.getState());
				pr.setValidDate(pactRenew.getValidDate());
				pr.setMakeDate(pactRenew.getMakeDate());
				pr.setMakePerson(pactRenew.getMakePerson());
				pr.setCheckDate(pactRenew.getCheckDate());
				pr.setCheckPerson(pactRenew.getCheckPerson());
				pr.setConfirmDate(operTime);
				pr.setConfirmPerson(person);
				pr.setYearMonth(pactRenew.getYearMonth());
				super.save(pr);
				
				pact.setPactState(2);
				pact.setSignTimes(pact.getSignTimes()+1);
				pact.setSignYear(pactRenew.getRenewYear());
				Calendar cal = Calendar.getInstance();
				cal.setTime(pact.getEndDate());
				cal.add(Calendar.YEAR, pactRenew.getRenewYear());
				pact.setBeginDate(pact.getEndDate());
				pact.setEndDate(cal.getTime());
				pact.setCompSignDate(operTime);
				pact.setCompSignPeople(person.getName());
				pactDao.save(pact);
				//在人员的合同子集处增加续签记录
				hrPersonHis = pact.getHrPersonHis();
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changeType", "续签合同");
				hrSubDataMap.put("changePerson", person.getName());
				hrSubDataMap.put("pactNo", pact.getCode());
				String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", operTime);
				hrSubDataMap.put("changeDate", changeDate);
				hrSubDataMap.put("remark", "合同被续签");
				sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			
			}
		}
	}

	@Override
	public void remove(String[] ids) {
		Pact pact = null;
		for(String id:ids){
			pact = this.pactRenewDao.get(id).getPact();
			pact.setLockType(Pact.LOCK_NO);
			this.pactDao.save(pact);
			//解锁
			String lockState="XQ";
			String personId=pact.getHrPerson().getPersonId();
			String pactId=pact.getId();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactRenew> pactRenewsTemp=pactRenewDao.getByFilters(filters);
			if(pactRenewsTemp!=null&&pactRenewsTemp.size()==1){
				hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactRenewsTemp=pactRenewDao.getByFilters(filters);
			if(pactRenewsTemp!=null&&pactRenewsTemp.size()==1){
				pactManager.unlockPact(pactId, lockState);
			}
		}
		super.remove(ids);
	}
}