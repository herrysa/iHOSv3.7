package com.huge.ihos.hr.hrDeptment.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDeptNewManager extends GenericManager<HrDeptNew, String> {
     public JQueryPager getHrDeptNewCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 保存并执行
      * @param hrDeptNew
      * @param person 操作人
      * @param operDate 操作时间
      */
     public HrDeptNew saveAndConfirm(HrDeptNew hrDeptNew,Person person,Date operDate,boolean asyncData);
     public void auditHrDeptNew(List<String> checkIds,Person person,Date date);
     public void doneHrDeptNew(List<String> doneIds,Person person,Date date,boolean ansycData);
     public void antiHrDeptNew(List<String> cancelCheckIds);
     public void deleteHrDeptNew(List<String> delIds);
}