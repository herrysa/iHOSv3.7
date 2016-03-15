package com.huge.ihos.hr.recruitNeed.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface RecruitNeedManager extends GenericManager<RecruitNeed, String> {
     public JQueryPager getRecruitNeedCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditRecruitNeed(List<String> checkIds,Person person,Date date);
     public void antiRecruitNeed(List<String> cancelCheckIds);
     /**
      * 由招聘需求生成招聘计划
      * @param addPlanCheckIds
      * @param person
      * @param date
      */
     public void addPlanRecruitNeed(List<String> addPlanCheckIds,Person person,Date date,String yearMonth);
     public RecruitNeed saveRecruitNeed(RecruitNeed recruitNeed,Boolean isEntityIsNew);
}