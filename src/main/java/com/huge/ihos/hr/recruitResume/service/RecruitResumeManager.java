package com.huge.ihos.hr.recruitResume.service;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.recruitResume.model.RecruitResume;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface RecruitResumeManager extends GenericManager<RecruitResume, String> {
     public JQueryPager getRecruitResumeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 添加到人才库
      * @param checkIds
      * @param person
      * @param date
      */
     public void talentPool(List<String> checkIds,Person person,Date date);
     /**
      * 添加到收藏夹
      * @param checkIds
      * @param person
      * @param date
      */
     public void favorite(List<String> checkIds,Person person,Date date);
     /**
      * 审查合格
      * @param checkIds
      * @param person
      * @param date
      */
     public void qualified(List<String> checkIds,Person person,Date date);
     /**
      * 面试通过
      * @param checkIds
      */
     public void pass(List<String> checkIds);
     /**
      *面试不合格
      * @param checkIds
      */
     public void dispass(List<String> checkIds);
     /**
      * 移出收藏夹或者人才库
      * @param checkIds
      */
     public void removeOut(List<String> checkIds);
     /**
      * 录用
      * @param employIds
      * @param person
      */
     public void employ(List<String> employIds,Person person,String yearMonth,HttpServletRequest req) throws Exception;
     public RecruitResume saveRecruitResume(RecruitResume recruitResume,Boolean isEntityIsNew);
     public RecruitResume saveRecruitResume(RecruitResume recruitResume,Boolean isEntityIsNew,String imagePath,HttpServletRequest req)throws Exception;
}