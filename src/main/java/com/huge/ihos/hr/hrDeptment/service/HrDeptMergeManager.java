package com.huge.ihos.hr.hrDeptment.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDeptMergeManager extends GenericManager<HrDeptMerge, String> {
     public JQueryPager getHrDeptMergeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 确认合并部门
      * @param hrDeptMerge 合并信息
      * @param person 操作人
      * @param date 操作时间
      */
	 public HrDepartmentSnap confirmMerge(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData);
	 /**
	  * 确认划转部门
	  * @param hrDeptMerge 划转信息
	  * @param person 操作人
	  * @param date 操作时间
	  */
	 public void confirmTransfer(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData);
	 /**
	  * 检测被合并的部门下是否存在岗位和人员
	  * @param hrDeptMerge
	  * @return true：存在；false：不存在
	  */
	 public boolean existPostAndPerson(HrDeptMerge hrDeptMerge);
	 public void auditHrDeptMerge(List<String> checkIds,Person person,Date date);
	 public List<String> doneHrDeptMerge(List<String> doneIds,Person person,Date date,boolean ansycData);
	 public void antiHrDeptMerge(List<String> cancelCheckIds);
	 public void deleteHrDeptMerge(List<String> delIds);
}