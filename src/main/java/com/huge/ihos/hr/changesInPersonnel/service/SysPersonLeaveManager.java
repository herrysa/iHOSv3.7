package com.huge.ihos.hr.changesInPersonnel.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysPersonLeaveManager extends GenericManager<SysPersonLeave, String> {
     public JQueryPager getSysPersonLeaveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 审核离职单
      * @param checkIds
      * @param person
      * @param date
      */
     public void auditSysPersonLeave(List<String> checkIds,Person person,Date date);
     /**
      * 销审离职单
      * @param cancelCheckIds
      */
     public void antiSysPersonLeave(List<String> cancelCheckIds);
     /**
      * 执行离职
      * @param doneIds
      * @param person
      * @param date
      * @param ansycData
      */
     public void doneSysPersonLeave(List<String> doneIds,Person person,Date date,boolean ansycData);
     /**
      * 离职后解除合同
      * @param doneIds
      * @param person
      * @param date
      */

     public void doneChangePact(List<String> doneIds,Person person,Date date,String yearMonth);
     /**
      * 不走审核流程直接离职
      * @param sysPersonLeave
      * @param person
      * @param date
      * @param isEntityIsNew
      * @param ansycData
      * @return
      */
     public SysPersonLeave saveAndDoneSysPersonLeave(SysPersonLeave sysPersonLeave,Person person,Date date,Boolean isEntityIsNew,boolean ansycData);
     /**
      * 保存离职单
      * @param sysPersonLeave
      * @param isEntityIsNew
      * @return
      */
     public SysPersonLeave saveSysPersonLeave(SysPersonLeave sysPersonLeave,Boolean isEntityIsNew);
     /**
      * 删除离职单
      * @param delIds
      */
     public void deleteSysPersonLeave(List<String> delIds);
}