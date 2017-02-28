package com.huge.ihos.material.deptplan.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.ihos.material.deptplan.dao.DeptNeedPlanDao;
import com.huge.ihos.material.deptplan.dao.DeptNeedPlanDetailDao;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.service.DeptNeedPlanManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("deptNeedPlanManager")
public class DeptNeedPlanManagerImpl extends GenericManagerImpl<DeptNeedPlan, String> implements DeptNeedPlanManager {
    private DeptNeedPlanDao deptNeedPlanDao;
    private DeptNeedPlanDetailDao deptNeedPlanDetailDao;
    private BillNumberManager billNumberManager;
	private DeptAppDetailManager deptAppDetailManager;
    
	@Autowired
    public DeptNeedPlanManagerImpl(DeptNeedPlanDao deptNeedPlanDao) {
        super(deptNeedPlanDao);
        this.deptNeedPlanDao = deptNeedPlanDao;
    }
    
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    
    @Autowired
    public void setDeptNeedPlanDetailDao(DeptNeedPlanDetailDao deptNeedPlanDetailDao) {
		this.deptNeedPlanDetailDao = deptNeedPlanDetailDao;
	}

	public JQueryPager getDeptNeedPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptNeedPlanDao.getDeptNeedPlanCriteria(paginatedList,filters);
	}
	
	@Autowired
    public void setDeptAppDetailManager(DeptAppDetailManager deptAppDetailManager) {
		this.deptAppDetailManager = deptAppDetailManager;
	}
	
	@Override
	public DeptNeedPlan saveDeptDeedPlan(DeptNeedPlan deptNeedPlan,DeptNeedPlanDetail[] deptNeedPlanDetails) {
		
		if(OtherUtil.measureNull(deptNeedPlan.getNeedId())){
			deptNeedPlan.setNeedId(null);
		}
		if(deptNeedPlan.getNeedNo()==null || deptNeedPlan.getNeedNo().trim().length()==0){
			//String tempNeedNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.DEPT_NEED_PLAN, deptNeedPlan.getOrgCode(), deptNeedPlan.getCopyCode(), deptNeedPlan.getPeriodMonth(),true);
			String tempNeedNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.DEPT_NEED_PLAN, true, deptNeedPlan.getOrgCode(), deptNeedPlan.getCopyCode(),deptNeedPlan.getPeriodMonth());
			deptNeedPlan.setNeedNo(tempNeedNo);
		}
		Set<DeptNeedPlanDetail> dnpds = null;
		if(deptNeedPlanDetails.length>0){
			dnpds = new HashSet<DeptNeedPlanDetail>();
		}
		for(int i=0,len = deptNeedPlanDetails.length;i<len;i++ ){
			deptNeedPlanDetails[i].setDeptNeedPlan(deptNeedPlan);
			dnpds.add(deptNeedPlanDetails[i]);
		}
		if(deptNeedPlan.getDeptNeedPlanDetails()!=null){
			deptNeedPlan.getDeptNeedPlanDetails().clear(); 
		}
		deptNeedPlan.setDeptNeedPlanDetails(dnpds);
		
		deptNeedPlan = this.deptNeedPlanDao.save(deptNeedPlan);
		return deptNeedPlan;
	}

	@Override
	public void auditDeptDeedPlan(List<String> checkIds,Person person,Date date) {
		DeptNeedPlan deptNeedPlan = null;
		Set<DeptNeedPlanDetail> deptNeedPlanDetails = null;
		for(String checkId:checkIds){
			deptNeedPlan = deptNeedPlanDao.get(checkId);
			deptNeedPlan.setState("1");
			deptNeedPlan.setCheckPerson(person);
			deptNeedPlan.setCheckDate(date);
			deptNeedPlanDao.save(deptNeedPlan);
			deptNeedPlanDetails = deptNeedPlan.getDeptNeedPlanDetails();
			for(DeptNeedPlanDetail deptNeedPlanDetail:deptNeedPlanDetails){
				deptNeedPlanDetail.setSumAmount(deptNeedPlanDetail.getSumAmount()+deptNeedPlanDetail.getAmount());
				deptNeedPlanDetailDao.save(deptNeedPlanDetail);
			}
		}
	}

	@Override
	public void antiAuditDeptNeedPlan(List<String> cancelCheckIds) {
		DeptNeedPlan deptNeedPlan = null;
		Set<DeptNeedPlanDetail> deptNeedPlanDetails = null;
		for(String cancelCheckId:cancelCheckIds){
			deptNeedPlan = deptNeedPlanDao.get(cancelCheckId);
			deptNeedPlan.setState("0");
			deptNeedPlan.setCheckPerson(null);
			deptNeedPlan.setCheckDate(null);
			deptNeedPlanDao.save(deptNeedPlan);
			deptNeedPlanDetails = deptNeedPlan.getDeptNeedPlanDetails();
			for(DeptNeedPlanDetail deptNeedPlanDetail:deptNeedPlanDetails){
				deptNeedPlanDetail.setSumAmount(deptNeedPlanDetail.getSumAmount()-deptNeedPlanDetail.getAmount());
				deptNeedPlanDetailDao.save(deptNeedPlanDetail);
			}
		}
	}

	@Override
	public void remove(String id) {
		DeptNeedPlan deptNeedPlan = this.get(id);
		deptAppDetailManager.setNeedNoToDetail(deptNeedPlan.getNeedNo(), null, "delete");
		super.remove(id);
		UserContext userContext = UserContextUtil.getUserContext();
		String orgCode = userContext.getOrgCode();
		String copyCode = userContext.getCopyCode();
		String period = userContext.getPeriodMonth();
		//invBillNumberManager.updateSerialNumber(InvBillNumberSetting.DEPT_NEED_PLAN,orgCode, copyCode,period);
	}

	@Override
	public DeptNeedPlan saveDeptDeedPlanFromDeptApp(DeptNeedPlan deptNeedPlan,DeptNeedPlanDetail[] deptNeedPlanDetails, String deptAppDetailIds) {
		deptNeedPlan = this.saveDeptDeedPlan(deptNeedPlan, deptNeedPlanDetails);
		deptAppDetailManager.setNeedNoToDetail(deptNeedPlan.getNeedNo(), deptAppDetailIds, "save");
		return deptNeedPlan;
	}
}