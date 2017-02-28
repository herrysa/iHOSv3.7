package com.huge.ihos.material.deptplan.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.deptplan.dao.DeptMRSummaryDao;
import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("deptMRSummaryManager")
public class DeptMRSummaryManagerImpl extends GenericManagerImpl<DeptMRSummary, String> implements DeptMRSummaryManager {
    private DeptMRSummaryDao deptMRSummaryDao;

    private BillNumberManager billNumberManager;

   
	@Autowired
    public DeptMRSummaryManagerImpl(DeptMRSummaryDao deptMRSummaryDao) {
        super(deptMRSummaryDao);
        this.deptMRSummaryDao = deptMRSummaryDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getDeptMRSummaryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptMRSummaryDao.getDeptMRSummaryCriteria(paginatedList,filters);
	}
    public DeptMRSummary saveDeptMRSummary(DeptMRSummary deptMRSummary,DeptMRSummaryDetail[] deptMRSummaryDetails){
    	if(OtherUtil.measureNull(deptMRSummary.getMrId())){
    		deptMRSummary.setMrId(null);
		}
		if(deptMRSummary.getMrNo() ==null || deptMRSummary.getMrNo().trim().length()==0){
			//String tempMrNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.DEPT_MR_SUMMARY, deptMRSummary.getOrgCode(), deptMRSummary.getCopyCode(), deptMRSummary.getPeriodMonth(),true);
			String tempMrNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.DEPT_MR_SUMMARY, true, deptMRSummary.getOrgCode(), deptMRSummary.getCopyCode(),deptMRSummary.getPeriodMonth());
			deptMRSummary.setMrNo(tempMrNo);
		}
		Set<DeptMRSummaryDetail> dmrsds = null;
		if(deptMRSummaryDetails.length>0){
			dmrsds = new HashSet<DeptMRSummaryDetail>();
		}
		for(int i=0,len = deptMRSummaryDetails.length;i<len;i++ ){
			deptMRSummaryDetails[i].setDeptMRSummary(deptMRSummary);
			dmrsds.add(deptMRSummaryDetails[i]);
		}
		if(deptMRSummary.getDeptMRSummaryDetails()!=null){
			deptMRSummary.getDeptMRSummaryDetails().clear(); 
		}
		deptMRSummary.setDeptMRSummaryDetails(dmrsds);
		
		deptMRSummary = this.deptMRSummaryDao.save(deptMRSummary);
		return deptMRSummary;
    }
}