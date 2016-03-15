package com.huge.ihos.hr.hrDeptment.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDepartmentCurrentManager extends GenericManager<HrDepartmentCurrent, String> {
     public JQueryPager getHrDepartmentCurrentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public JQueryPager getHrDepartmentCriteriaForSetPlan(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 获取所有下级部门
      * @param deptId
      * @return
      */
	 public List<HrDepartmentCurrent> getAllDescendants(String deptId);
	 /**
	  * 根据orgCode获取其下的所有部门Id;根据deptId获取其下的所有部门Id(包括自身);
	  * @param orgCode
	  * @param deptId
	  * @return
	  */
	 public String getAllDeptIds(String orgCode,String deptId);
	 /**
	  * 根据orgCode获取其下的所有部门Code;根据deptId获取其下的所有部门Code(包括自身);
	  * @param orgCode
	  * @param deptId
	  * @return
	  */
	 public String[] getAllDeptCodes(String orgCode,String deptId,Boolean showDisableDept);
	 /**
	  * 检查锁的状态
	  * @param deptId
	  * @param checkLockStates
	  * @return
	  */
	 public String checkLockHrDepartmentCurrent(String deptId,String[] checkLockStates);
	 
	 /**
	  * 计算实有人员数、实有在编人员数
	  * @param deptId
	  * @return
	  */
	 public void computePersonCount();
	 /**
	  * 计算实有人员数、实有在编人员数
	  * @param personId
	  * @return
	  */
	 public void computePersonCount(String deptId);
	 /**
	  * 计算实有人员数、实有在编人员数
	  * @param personId
	  * @return
	  */
	 //public void computePersonCount(HrPersonSnap person);
	 
	 /**
	  * 将编制人数存储至DepartMentSnap中
	  * @param info(里面包含id和planCount)
	  * @param 
	  * @return
	  */
	 public void doSaveDepartMentSnap(String info);
	 /**
	  * 获取deptId和SnapCode的Map
	  * @return
	  */
	 public Map<String, String> getDeptIdAndSnapCodeMap();
	 /**
	  * 获取合计值
	  * @return
	  */
	public List<Integer> getSUM(String string, List<PropertyFilter> filters);
}