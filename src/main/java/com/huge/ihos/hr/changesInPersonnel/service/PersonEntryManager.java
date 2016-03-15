package com.huge.ihos.hr.changesInPersonnel.service;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.changesInPersonnel.model.PersonEntry;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PersonEntryManager extends GenericManager<PersonEntry, String> {
     public JQueryPager getPersonEntryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 审核入职单
      * @param checkIds
      * @param person
      * @param date
      */
     public void auditPersonEntry(List<String> checkIds,Person person,Date date);
     /**
      * 销审入职单
      * @param cancelCheckIds
      */
     public void antiPersonEntry(List<String> cancelCheckIds);
     /**
      * 执行入职单
      * @param doneIds
      * @param person
      * @param ansycData
      */
     public void donePersonEntry(List<String> doneIds,Person person,boolean ansycData,HttpServletRequest req) throws Exception;
     /**
      * 执行入职后为人员添加合同
      * @param donePactIds
      * @param person
      */
     public void donePactPersonEntry(List<String> donePactIds,Person person,String yearMonth);
     /**
      * 不走审核流程直接入职
      * @param personEntry
      * @param person
      * @param isEntityIsNew
      * @param ansycData
      * @return
      */
     public PersonEntry saveAndDonePersonEntry(PersonEntry personEntry,Person person,Boolean isEntityIsNew,boolean ansycData,String imagePath,HttpServletRequest req) throws Exception;
     /**
      * 保存入职单
      * @param personEntry
      * @param isEntityIsNew
      * @return
      */
     public PersonEntry savePersonEntry(PersonEntry personEntry,Boolean isEntityIsNew);
     public PersonEntry savePersonEntry(PersonEntry personEntry,Boolean isEntityIsNew,String imagePath,HttpServletRequest req) throws Exception;
     /**
      * 删除入职单
      * @param delIds
      */
     public void deletePersonEntry(List<String> delIds);
}