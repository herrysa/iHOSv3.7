package com.huge.ihos.material.deptplan.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptNeedPlanManager extends GenericManager<DeptNeedPlan, String> {
     public JQueryPager getDeptNeedPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	 public DeptNeedPlan saveDeptDeedPlan(DeptNeedPlan deptNeedPlan,DeptNeedPlanDetail[] deptNeedPlanDetails);
	 public void auditDeptDeedPlan(List<String> checkIds,Person person,Date date);
	 public void antiAuditDeptNeedPlan(List<String> cancelCheckIds);
	 /**
	  * 删除记录，同时更新serialNumber
	  * @param removeId
	  * @param sv
	  */
	 public void remove(String removeId);
	 /**
	  * 保存从科室申领出生成的单据，并回写单据号至科室申领明细
	  * @param deptNeedPlan
	  * @param deptNeedPlanDetails
	  * @param deptAppDetailIds
	  * @return
	  */
	 public DeptNeedPlan saveDeptDeedPlanFromDeptApp(DeptNeedPlan deptNeedPlan,DeptNeedPlanDetail[] deptNeedPlanDetails, String deptAppDetailIds);
}