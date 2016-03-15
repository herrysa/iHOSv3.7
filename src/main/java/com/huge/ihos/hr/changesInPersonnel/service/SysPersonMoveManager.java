package com.huge.ihos.hr.changesInPersonnel.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysPersonMoveManager extends GenericManager<SysPersonMove, String> {
     public JQueryPager getSysPersonMoveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 审核人员调动
      * @param checkIds 调动单Ids
      * @param person 审核人
      * @param date 审核日期
      */
     public void auditSysPersonMove(List<String> checkIds,Person person,Date date);
     /**
      * 销审人员调动
      * @param cancelCheckIds 调动单Ids
      */
     public void antiSysPersonMove(List<String> cancelCheckIds);
     /**
      * 执行人员调动
      * @param doneIds 调动单Ids
      * @param person 执行人
      * @param date 执行日期
      */
     public void doneSysPersonMove(List<String> doneIds,Person person,Date date,boolean ansycData);
     /**
      * 人员调动后改动人员对应的合同信息
      * @param doneIds
      */
     public void doneChangePact(List<String> doneIds);
     /**
      * 不走审核直接调动人员
      * @param sysPersonMove
      * @param person
      * @param date
      * @param isEntityIsNew
      * @param ansycData
      * @return
      */
     public SysPersonMove saveAndDoneSysPersonMove(SysPersonMove sysPersonMove,Person person,Date date,Boolean isEntityIsNew,boolean ansycData);
     /**
      * 保存调动单
      * @param sysPersonMove
      * @param isEntityIsNew
      * @return
      */
     public SysPersonMove saveSysPersonMove(SysPersonMove sysPersonMove,Boolean isEntityIsNew);
     /**
      * 删除调动单
      * @param delIds
      */
     public void deleteSysPersonMove(List<String> delIds);
}