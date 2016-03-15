package com.huge.ihos.hr.hrOperLog.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrOperLogManager extends GenericManager<HrOperLog, String> {
     public JQueryPager getHrOperLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 增加操作log(单条记录)
      * @param entityInfo 一条实体变动信息
      * @param operPerson 操作人
      * @param operTime 操作时间
      */
     public <T> void addHrOperLog(HrLogEntityInfo<T> entityInfo,Person operPerson, Date operTime);
     /**
     * 添加操作log(多条记录)
     * @param entityInfoList 多条实体变动信息
     * @param operPerson 操作人
     * @param operTime 操作时间
     */
     public <T> void addHrOperLog(List<HrLogEntityInfo<T>> entityInfoList,Person operPerson, Date operTime);
}