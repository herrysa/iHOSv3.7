package com.huge.ihos.hr.sysTables.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SysTableContent table.
 */
public interface SysTableContentDao extends GenericDao<SysTableContent, String> {
	public JQueryPager getSysTableContentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void createTable(String code);
	public void deleteTable(String code);
	public List<SysTableContent> getFullSysTableContent(String orgCode,String tableKindCode);
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
}