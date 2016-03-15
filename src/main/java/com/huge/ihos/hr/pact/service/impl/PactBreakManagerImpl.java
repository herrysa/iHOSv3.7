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
import com.huge.ihos.hr.pact.dao.PactBreakDao;
import com.huge.ihos.hr.pact.dao.PactDao;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.model.PactBreak;
import com.huge.ihos.hr.pact.service.PactBreakManager;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("pactBreakManager")
public class PactBreakManagerImpl extends GenericManagerImpl<PactBreak, String> implements PactBreakManager {
    private PactBreakDao pactBreakDao;
    private PactDao pactDao;
    private BillNumberManager billNumberManager;
    private SysTableStructureManager sysTableStructureManager;
    private PactManager pactManager;
    private HrPersonSnapManager hrPersonSnapManger;
    
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
    public PactBreakManagerImpl(PactBreakDao pactBreakDao) {
        super(pactBreakDao);
        this.pactBreakDao = pactBreakDao;
    }
    @Autowired
    public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}
    @Autowired
	public void setHrPersonSnapManger(HrPersonSnapManager hrPersonSnapManger) {
		this.hrPersonSnapManger = hrPersonSnapManger;
	}
    public JQueryPager getPactBreakCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return pactBreakDao.getPactBreakCriteria(paginatedList,filters);
	}

	@Override
	public void addPactBreak(String pactIds, PactBreak pactBreak) {
		String[] pacts = pactIds.split(",");
		PactBreak pb = null;
		Pact pact = null;
		for(String pactId:pacts){
			if("".equals(pactId)){
				continue;
			}
			pb = new PactBreak();
			pact = pactDao.get(pactId);
			pb.setPact(pact);
			pb.setRemark(pactBreak.getRemark());
			pb.setBreakReason(pactBreak.getBreakReason());
			String breakNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_BREAK,pactBreak.getYearMonth());
			pb.setBreakNo(breakNo);
			pb.setState(pactBreak.getState());
			pb.setBreakDate(pactBreak.getBreakDate());
			pb.setMakeDate(pactBreak.getMakeDate());
			pb.setMakePerson(pactBreak.getMakePerson());
			pb.setYearMonth(pactBreak.getYearMonth());
			super.save(pb);
			pact.setLockType(Pact.LOCK_BREAK);
			pactDao.save(pact);
			//上锁
			String lockState="ZZ";
			String personId=pact.getHrPerson().getPersonId();
			pactManager.lockPact(pactId, lockState);
			hrPersonSnapManger.lockHrPerson(personId, lockState);
		}
	}
	

	@Override
	public void confirmBreak(String pactIds, PactBreak pactBreak, Date date,Person person) {
		HrPersonHis hrPersonHis = null;
		if(pactIds==null){
			pactBreak.setConfirmDate(date);
			pactBreak.setConfirmPerson(person);
			pactBreak = this.save(pactBreak);
			Pact pact = pactBreak.getPact();
			pact.setSignState(4);
			pact.setBreakDate(pactBreak.getBreakDate());
			pact.setBreakReason(pactBreak.getBreakReason());
			pact.setLockType(Pact.LOCK_NO);
			pactDao.save(pact);
			hrPersonHis = pact.getHrPersonHis();
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "终止合同");
			hrSubDataMap.put("pactNo", pact.getCode());
			hrSubDataMap.put("changePerson", person.getName());
			String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date);
			hrSubDataMap.put("changeDate", changeDate);
			hrSubDataMap.put("remark", "合同被终止");
			sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			//解锁
			String pactId=pact.getId();
			String personId=pact.getHrPerson().getPersonId();
			String lockState="ZZ";
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactBreak> pactBreaksTemp=pactBreakDao.getByFilters(filters);
			if(pactBreaksTemp==null||pactBreaksTemp.size()==0){
				hrPersonSnapManger.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactBreaksTemp=pactBreakDao.getByFilters(filters);
			if(pactBreaksTemp==null||pactBreaksTemp.size()==0){
				pactManager.unlockPact(pactId, lockState);
			}
			
		}else{
			String[] pacts = pactIds.split(",");
			PactBreak pb = null;
			Pact pact = null;
			for(String pactId:pacts){
				if("".equals(pactId)){
					continue;
				}
				pb = new PactBreak();
				pact = pactDao.get(pactId);
				pb.setPact(pact);
				pb.setRemark(pactBreak.getRemark());
				pb.setBreakReason(pactBreak.getBreakReason());
				String breakNo = billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_BREAK,pactBreak.getYearMonth());
				pb.setBreakNo(breakNo);
				pb.setState(pactBreak.getState());
				pb.setBreakDate(pactBreak.getBreakDate());
				pb.setMakeDate(pactBreak.getMakeDate());
				pb.setMakePerson(pactBreak.getMakePerson());
				pb.setCheckDate(pactBreak.getCheckDate());
				pb.setCheckPerson(pactBreak.getCheckPerson());
				pb.setConfirmDate(date);
				pb.setConfirmPerson(person);
				pb.setYearMonth(pactBreak.getYearMonth());
				super.save(pb);
				
				pact.setSignState(4);
				pact.setBreakDate(pactBreak.getBreakDate());
				pact.setBreakReason(pactBreak.getBreakReason());
				pactDao.save(pact);
				hrPersonHis = pact.getHrPersonHis();
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changePerson", person.getName());
				hrSubDataMap.put("changeType", "终止合同");
				hrSubDataMap.put("pactNo", pact.getCode());
				String changeDate = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", date);
				hrSubDataMap.put("changeDate", changeDate);
				hrSubDataMap.put("remark", "合同被终止");
				sysTableStructureManager.insertHrSubData("hr_person_pactInfo",hrPersonHis.getHrPersonPk().getPersonId(), hrPersonHis.getHrPersonPk().getSnapCode(), hrSubDataMap);
			}
		}
		
	}

	@Override
	public void remove(String[] ids) {
		Pact pact = null;
		for(String id:ids){
			pact = this.pactBreakDao.get(id).getPact();
			pact.setLockType(Pact.LOCK_NO);
			this.pactDao.save(pact);
			//解锁
			String pactId=pact.getId();
			String personId=pact.getHrPerson().getPersonId();
			String lockState="ZZ";
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_pact.hrPerson.personId", personId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			List<PactBreak> pactBreaksTemp=pactBreakDao.getByFilters(filters);
			if(pactBreaksTemp!=null&&pactBreaksTemp.size()==1){
				hrPersonSnapManger.unlockHrPersonSnap(personId, lockState);
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_pact.id", pactId));
			filters.add(new PropertyFilter("INI_state", "1,2"));
			pactBreaksTemp=pactBreakDao.getByFilters(filters);
			if(pactBreaksTemp!=null&&pactBreaksTemp.size()==1){
				pactManager.unlockPact(pactId, lockState);
			}
		}
		super.remove(ids);
	}
}