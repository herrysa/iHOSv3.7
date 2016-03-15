package com.huge.ihos.hr.hrDeptment.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDeptRescind;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDeptRescindManager extends GenericManager<HrDeptRescind, String> {
     public JQueryPager getHrDeptRescindCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 保存并执行
      * @param hrDeptRescind
      * @param person 操作人
      * @param operDate 操作时间
      */
     public HrDeptRescind saveAndConfirm(HrDeptRescind hrDeptRescind,Person person,Date operDate,boolean asyncData);
     public void auditHrDeptRescind(List<String> checkIds,Person person,Date date);
   	 public void doneHrDeptRescind(List<String> doneIds,Person person,Date date,boolean ansycData);
   	 public void antiHrDeptRescind(List<String> cancelCheckIds);
}