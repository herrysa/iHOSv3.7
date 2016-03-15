package com.huge.ihos.hr.recruitPlan.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface RecruitPlanManager extends GenericManager<RecruitPlan, String> {
     public JQueryPager getRecruitPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditRecruitPlan(List<String> checkIds,Person person,Date date);
     public void antiRecruitPlan(List<String> cancelCheckIds);
     /**
      * 发布招聘计划
      * @param confirmCheckIds
      * @param person
      * @param date
      */
     public void confirmRecruitPlan(List<String> confirmCheckIds,Person person,Date date);
     public void breakRecruitPlan(List<String> breakIds,Person person,Date date);
     public RecruitPlan saveRecruitPlan(RecruitPlan recruitPlan,Boolean isEntityIsNew,Person person);
     public void deleteRecruitPlan(List<String> delIds);
}