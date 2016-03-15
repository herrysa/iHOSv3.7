package com.huge.ihos.hr.sysTables.service;


import java.util.List;

import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysTableContentManager extends GenericManager<SysTableContent, String> {
     public JQueryPager getSysTableContentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 建表
      * @param tableContent主键
      */
     public void createTable(String code);
     /**
      * 删除表
      * @param  tableConten主键数组
      */
     public void deleteTable(String[] ids);
     /**
   	 * SysTableContent机构树状图
   	 * @param orgCode,tableKindCode
   	 * @return List<SysTableContent>
   	 */
   	 public List<SysTableContent> getFullSysTableContent(String orgCode,String tableKindCode);
   	 /**
   	  * 通过主表名字获取该类型的附加表列表
   	  * @param mainTable
   	  * @return
   	  */
   	 public List<SysTableContent> getTableContentByMainTable(String mainTable);
   	/**
 	 * 单项统计获取table列表
 	 * @param statisticsCode
 	 * @return
 	 */
 	public List<BdInfo> getSingleFieldTableList(String statisticsCode);
 	/**
	 * 删除与其关联的所有附属信息
	 * @param mainTable 主表名
	 * @param foreignId 外键关联ID
	 */
	public void deleteAllSubSetDataByFK(String mainTable,String foreignId);
	public void saveTableContent(SysTableContent sysTableContent,Person person,Boolean isEntityIsNew);
	public void deleteTableContent(String[] ida,String[] bdInfoIda);
}