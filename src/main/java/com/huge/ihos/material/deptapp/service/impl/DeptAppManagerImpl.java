package com.huge.ihos.material.deptapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InvBatchDao;
import com.huge.ihos.material.deptapp.dao.DeptAppDao;
import com.huge.ihos.material.deptapp.dao.DeptAppDetailDao;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDis;
import com.huge.ihos.material.deptapp.model.DeptAppDis.BatchDis;
import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.ihos.material.deptapp.service.DeptAppDisLogManager;
import com.huge.ihos.material.deptapp.service.DeptAppManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateConverter;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("deptAppManager")
public class DeptAppManagerImpl extends GenericManagerImpl<DeptApp, String> implements DeptAppManager {
    private DeptAppDao deptAppDao;
    //private InvBillNumberManager invBillNumberManager;
    private BillNumberManager billNumberManager;
	private DeptAppDetailDao deptAppDetailDao;
    private GlobalParamManager globalParamManager;
    private DeptAppDisLogManager deptAppDisLogManager;
    private InvBatchDao invBatchDao;
    
    @Autowired
    public DeptAppManagerImpl(DeptAppDao deptAppDao) {
        super(deptAppDao);
        this.deptAppDao = deptAppDao;
    }
    
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    
    @Autowired
	public void setGlobalParamManager(GlobalParamManager globalParamManager) {
		this.globalParamManager = globalParamManager;
	}
    
	@Autowired
    public void setDeptAppDetailDao(DeptAppDetailDao deptAppDetailDao) {
		this.deptAppDetailDao = deptAppDetailDao;
	}
	
	@Autowired
	public void setDeptAppDisLogManager(DeptAppDisLogManager deptAppDisLogManager) {
		this.deptAppDisLogManager = deptAppDisLogManager;
	}

	@Autowired
	public void setInvBatchDao(InvBatchDao invBatchDao) {
		this.invBatchDao = invBatchDao;
	}

	public JQueryPager getDeptAppCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptAppDao.getDeptAppCriteria(paginatedList,filters);
	}
	
	@Override
	public void endDeptApp(List<DeptApp> deptApps) {
		String state = null;
		Set<DeptAppDetail> dads = null;
		for(DeptApp deptApp:deptApps){
			state = deptApp.getAppState();
			if(state.equals("3")){
				deptApp.setAppState("8");
			}else if(state.equals("6")){
				deptApp.setAppState("7");
			}
			dads = deptApp.getDeptAppDetails();
			for(DeptAppDetail dad:dads){
				dad.setNoThroughAmount(dad.getWaitingAmount());
				dad.setWaitingAmount(0d);
				dad.setThroughAmount(0d);
				this.deptAppDetailDao.save(dad);
			}
			this.deptAppDao.save(deptApp);
		}
	}

	@Override
	public DeptApp saveDeptApp(DeptApp deptApp, DeptAppDetail[] deptAppDetails,String businessDate,Person person) {
		if(OtherUtil.measureNull(deptApp.getDeptAppId())){
			deptApp.setDeptAppId(null);
			String autoCheck = globalParamManager.getGlobalParamByKey("deptAppAutoCheck");
			if(autoCheck!=null && autoCheck.equals("1")){
				deptApp.setAppState("1");
				deptApp.setAppChecker(person);
				deptApp.setCheckDate((Date) (new DateConverter().convert(Date.class, businessDate)));
			}
		}
		if(deptApp.getAppNo()==null || deptApp.getAppNo().trim().length()==0){
			String tempAppNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.DEPT_APP, true, deptApp.getOrgCode(), deptApp.getCopyCode(),deptApp.getYearMonth());
			//String tempAppNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.DEPT_APP, deptApp.getOrgCode(), deptApp.getCopyCode(), deptApp.getYearMonth(),true);
			deptApp.setAppNo(tempAppNo);
		}
		Set<DeptAppDetail> dads = null;
		if(deptAppDetails.length>0){
			dads = new HashSet<DeptAppDetail>();
		}
		for(int i=0,len = deptAppDetails.length;i<len;i++ ){
			deptAppDetails[i].setDeptApp(deptApp);
			deptAppDetails[i].setWaitingAmount(deptAppDetails[i].getAppAmount());
			dads.add(deptAppDetails[i]);
		}
		if(deptApp.getDeptAppDetails()!=null){
			deptApp.getDeptAppDetails().clear(); 
		}
		deptApp.setDeptAppDetails(dads);
		
		deptApp = this.deptAppDao.save(deptApp);
		return deptApp;
	}
	/*
	 * 获取单据的状态，3(未处理)：直接比较
	 * 6(部分通过-其余待通过):加上已经通过的数量作比较
	 * 根据比较结果修改单据的状态，同时更新明细记录
	 */
	@Override
	public void executePass(Map<String, List<DeptAppDis>> map,Person person) {
		UserContext userContext = UserContextUtil.getUserContext();
		Date curDate = userContext.getBusinessDate();
		Date curTime = new Date();
		Iterator<Entry<String,List<DeptAppDis>>> ite = map.entrySet().iterator(); 
		String deptAppId = null;
		Entry<String,List<DeptAppDis>> entry = null;
		List<DeptAppDis> details = null;
		DeptApp deptApp = null;
		DeptAppDetail deptAppDetail = null;
		while(ite.hasNext()){
			entry = ite.next();
			deptAppId = entry.getKey();
			details = entry.getValue();
			deptApp = this.deptAppDao.get(deptAppId);
			for(DeptAppDis dis:details){
				if(dis.getThroughAmount().equals(new Double(0))){
					continue;
				}
				deptAppDetail = this.deptAppDetailDao.get(dis.getDeptAppDetailId());
//				deptAppDetail.setThroughAmount(detail.getThroughAmount());
				deptAppDetail.setWaitingAmount(deptAppDetail.getWaitingAmount()-dis.getThroughAmount());
				deptAppDetail.setRemark(dis.getRemark());
				deptAppDetail = this.deptAppDetailDao.save(deptAppDetail);
				//记录发放明细表
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_deptAppNo",deptApp.getAppNo()));
				filters.add(new PropertyFilter("EQS_orgCode",deptApp.getOrgCode()));
				filters.add(new PropertyFilter("EQS_copyCode",deptApp.getCopyCode()));
				filters.add(new PropertyFilter("EQS_yearMonth",deptApp.getYearMonth()));
				filters.add(new PropertyFilter("EQS_invDict.invId",deptAppDetail.getInvDict().getInvId()));
				List<DeptAppDisLog> disAppDisLogs = deptAppDisLogManager.getByFilters(filters);
				DeptAppDisLog disAppDisLog = null;
				if(disAppDisLogs!=null && disAppDisLogs.size()==1){
					disAppDisLog = disAppDisLogs.get(0);
				}
				Set<DeptAppDLDetail> deptAppDLDetails = new HashSet<DeptAppDLDetail>();
				if(disAppDisLog==null){
					disAppDisLog = new DeptAppDisLog();
					disAppDisLog.setOrgCode(deptApp.getOrgCode());
					disAppDisLog.setCopyCode(deptApp.getCopyCode());
					disAppDisLog.setYearMonth(userContext.getPeriodMonth());
					disAppDisLog.setDeptAppNo(deptApp.getAppNo());
					disAppDisLog.setStore(deptApp.getStore());
					disAppDisLog.setAppDept(deptApp.getAppDept());
					disAppDisLog.setInvDict(deptAppDetail.getInvDict());
					deptAppDLDetails = new HashSet<DeptAppDLDetail>();
				}else{
					deptAppDLDetails = disAppDisLog.getDeptAppDLDetails();
				}
				
				DeptAppDLDetail deptAppDLDetail = null;
				for(BatchDis batchDis:dis.getBatchDiss()){
					if(batchDis.getDisAmount().equals(new Double(0))){
						continue;
					}
					deptAppDLDetail = new DeptAppDLDetail();
					deptAppDLDetail.setDisTime(curTime);
					deptAppDLDetail.setDisPerson(person);
					deptAppDLDetail.setInvBatch(invBatchDao.get(batchDis.getBatchId()));
					deptAppDLDetail.setDisAmount(batchDis.getDisAmount());
					deptAppDLDetail.setDisPrice(batchDis.getDisPrice());
					deptAppDLDetail.setDeptAppDL(disAppDisLog);
					deptAppDLDetail.setOutNo(null);
					deptAppDLDetails.add(deptAppDLDetail);
				}
				disAppDisLog.setDeptAppDLDetails(deptAppDLDetails);
				deptAppDisLogManager.save(disAppDisLog);
			}
			
			Set<DeptAppDetail> deptAppDetails = deptApp.getDeptAppDetails();
			String state = "5";
			for(DeptAppDetail detail:deptAppDetails){
				if(!detail.getWaitingAmount().equals(new Double(0.0))){
					state = "6";
					break;
				}
			}
			deptApp.setAppState(state);
			deptApp.setStoreChecker(person);
			deptApp.setStoreCheckDate(curDate);
			this.deptAppDao.save(deptApp);
		}
	}

	@Override
	public void remove(String removeId) {
		super.remove(removeId);
		UserContext userContext = UserContextUtil.getUserContext();
		String orgCode = userContext.getOrgCode();
		String copyCode = userContext.getCopyCode();
		String period = userContext.getPeriodMonth();
		//invBillNumberManager.updateSerialNumber(InvBillNumberSetting.DEPT_APP, orgCode, copyCode, period);
	}

	@Override
	public void createFromHis(List<String> idl,Date date,Person person){
		DeptApp oldDeptApp = null,newDeptApp = null;
		for(String id:idl){
			oldDeptApp = this.deptAppDao.get(id);
			newDeptApp = copyDeptApp(oldDeptApp,date,person);
			deptAppDao.save(newDeptApp);
		}
	}
	
	private DeptApp copyDeptApp(DeptApp oldDeptApp,Date date,Person person){
		DeptApp newDeptApp = new DeptApp();
		newDeptApp.setOrgCode(oldDeptApp.getOrgCode());
		newDeptApp.setCopyCode(oldDeptApp.getCopyCode());
		newDeptApp.setYearMonth(oldDeptApp.getYearMonth());
		newDeptApp.setAppDate(date);
		newDeptApp.setAppDept(oldDeptApp.getAppDept());
		newDeptApp.setAppPerson(oldDeptApp.getAppPerson());
		newDeptApp.setStore(oldDeptApp.getStore());
		newDeptApp.setAppState("0");
		String autoCheck = globalParamManager.getGlobalParamByKey("deptAppAutoCheck");
		if(autoCheck!=null && autoCheck.equals("1")){
			newDeptApp.setAppState("1");
			newDeptApp.setAppChecker(person);
			newDeptApp.setCheckDate(date);
		}
		newDeptApp.setDocTemId(oldDeptApp.getDocTemId());
		String tempAppNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.DEPT_APP, true, oldDeptApp.getOrgCode(), oldDeptApp.getCopyCode(),oldDeptApp.getYearMonth());
		newDeptApp.setAppNo(tempAppNo);
		Set<DeptAppDetail> deptAppDetails = oldDeptApp.getDeptAppDetails();
		Set<DeptAppDetail> newDeptAppDetails = new HashSet<DeptAppDetail>();
		DeptAppDetail deptAppDetail = null;
		for(DeptAppDetail dad:deptAppDetails){
			deptAppDetail = new DeptAppDetail();
			deptAppDetail.setDeptApp(newDeptApp);
			deptAppDetail.setInvDict(dad.getInvDict());
			deptAppDetail.setAppAmount(dad.getAppAmount());
			deptAppDetail.setAppPrice(dad.getAppPrice());
			deptAppDetail.setAppMoney(dad.getAppMoney());
			deptAppDetail.setWaitingAmount(deptAppDetail.getAppAmount());
			newDeptAppDetails.add(deptAppDetail);
		}
		newDeptApp.setDeptAppDetails(newDeptAppDetails);
		return newDeptApp;
	}
	
}