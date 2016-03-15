package com.huge.ihos.hr.changesInPersonnel.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPostMove;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysPostMoveManager extends GenericManager<SysPostMove, String> {
     public JQueryPager getSysPostMoveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 审核调岗单
      * @param checkIds
      * @param person
      * @param date
      */
     public void auditSysPostMove(List<String> checkIds,Person person,Date date);
     /**
      * 销审调岗单
      * @param cancelCheckIds
      */
     public void antiSysPostMove(List<String> cancelCheckIds);
     /**
      * 执行调岗
      * @param doneIds
      * @param person
      * @param date
      * @param ansycData
      */
     public void doneSysPostMove(List<String> doneIds,Person person,Date date,boolean ansycData);
     /**
      * 调岗后变动人员对应合同的工作岗位
      * @param doneIds
      */
     public void doneChangePact(List<String> doneIds);
     /**
      * 不走审核直接调岗
      * @param sysPostMove
      * @param person
      * @param date
      * @param isEntityIsNew
      * @param ansycData
      * @return
      */
     public SysPostMove saveAndDoneSysPostMove(SysPostMove sysPostMove,Person person,Date date,Boolean isEntityIsNew,boolean ansycData);
     /**
      * 保存调岗
      * @param sysPostMove
      * @param isEntityIsNew
      * @return
      */
     public SysPostMove saveSysPostMove(SysPostMove sysPostMove,Boolean isEntityIsNew);
     /**
      * 删除调岗单
      * @param delIds
      */
     public void deleteSysPostMove(List<String> delIds);
}