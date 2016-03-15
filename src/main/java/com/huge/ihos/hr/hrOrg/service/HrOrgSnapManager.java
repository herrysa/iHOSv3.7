package com.huge.ihos.hr.hrOrg.service;

import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrOrgSnapManager extends GenericManager<HrOrgSnap, String> {
     public JQueryPager getHrOrgSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 保存单位snap的通用方法，该方法会记录log
      * @param hrOrgSnap
      * @param operDate
      * @param operPerson
      * @param syncData 是否实时同步数据
      * @return
      */
     public HrOrgSnap saveHrOrg(HrOrgSnap hrOrgSnap,Date operDate,Person operPerson,boolean syncData);
     /**
      * 删除单位
      * @param orgCode
      * @param operDate
      * @param operPerson
      * @param syncData
      */
     public void deleteHrOrg(String orgCode,Date operDate,Person operPerson,boolean syncData);
     
     /**
      * 根据时间戳获取单位对应的快照的snapId的String表示(用逗号分隔)
      * @param snapCode
      * @return null:表示没有对应的记录
      */
     public String getHisSnapIdString(String snapCode);
     /**
      * 根据时间戳获取对应单位快照的snapId的集合
      * @param snapCode
      * @return null:表示没有对应的记录
      */
     public List<String> getHisSnapIdList(String snapCode);
     /**
      * 根据时间戳获取对应单位快照
      * @param snapCode
      * @return
      */
     public List<HrOrgSnap> getOrgListByHisTime(String snapCode);
     
     /**
      * 同步单位
      * @param hrOrgSnap
      * @param operType
      */
     public void syncUpdateOrg(HrOrgSnap hrOrgSnap,String operType);
     
     public void syncUpdateHrOrg(String orgCode,String orgName,String snapCode,Person person,Date date);
     
     public List<HrOrgSnap> getAllHisAvailable(String snapCode);
     
     public List<HrOrgSnap> getAllDescendants(String orgCode,String snapCode);
}