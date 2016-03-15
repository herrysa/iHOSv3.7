package com.huge.ihos.hr.sysTables.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SysTableStructure table.
 */
public interface SysTableStructureDao extends GenericDao<SysTableStructure, String> {
	public JQueryPager getSysTableStructureCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	//创建新字段
	public void insertField(String id);
	//删除新字段
	public void deleteField(String id);
	public List<SysTableStructure> getTableStructureByTableCode(String tableCode);
	//查询表中数据
	public List<Object[]> getDataByTableCode(String tableCode);
	//查询表中数据
	public List<Object[]> getsysTableData(String code,String tableCode);
		
	public void deleteSystableData(String tableCode,String code);
	
	public JQueryPager getSubSets(JQueryPager pagedRequests,String subTableCode,List<PropertyFilter> filters,String tableKind);
	/**
	 * 科室/人员维护页面附属信息保存
	 * @param foreignId 关联外键
	 * @param snapCode  
	 * @param gridAllDatas  JSON数据
	 */
	public void saveHrSubData(String foreignId,String snapCode,Object gridAllDatas);
	/**
	 * 子集新增数据
	 * @param tableCode 表名
	 * @param foreignId 外键关联值
	 * @param snapCode snapCode
	 * @param hrSubDataMap 需要插入的Map集合
	 */
	public void insertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataMap);
	/**
	 * 子集更新数据，先查询，如果存在则更新，不存在则新增
	 * @param tableCode 表名
	 * @param foreignId 外键关联值
	 * @param snapCode snapCode
	 * @param hrSubDataWhereMap where条件Map
	 * @param operType 更新类型
	 * @param hrSubDataOperMap 跟新数据map
	 * @param hrSubDataMap 新增数据map
	 */
	public void updateOrInsertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataWhereMap,String operType,Map<String,String> hrSubDataOperMap,Map<String,String> hrSubDataMap);
	public void batchEditSave(String updateId,String tableCode,String gridAllDatas);
	public List<SysTableStructure> getTableStructureDataAndDigital(String tableCode);
}