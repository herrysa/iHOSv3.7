package com.huge.ihos.hr.sysTables.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysTableStructureManager extends GenericManager<SysTableStructure, String> {
     public JQueryPager getSysTableStructureCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
    /**
    * 数据库插入字段
    * @param tableStructure主键
    */
 	public void insertField(String id);
 	/**
 	 * 数据库删除字段
 	 * @param tableStructure主键数组
 	 */
 	public void deleteField(String[] ids);
 	//根据表名查出字段
 	public List<SysTableStructure> getTableStructureByTableCode(String tableCode);
 	//根据表名查出数据
 	public List<Object[]> getDataByTableCode(String tableCode);
 	//根据表名，主键查出一条记录
 	public List<Object[]> getsysTableData(String code,String tableCode);
	/**
	 * 获取附属子集的数据
	 * @param pagedRequests
	 * @param orgCode 单位code
	 * @param subTableCode 附属子集表名
	 * @param filters 过滤条件
	 * @return
	 */
	public JQueryPager getSubSets(JQueryPager pagedRequests,String subTableCode,List<PropertyFilter> filters,String tableKind);
	//删除附属信息数据
	public void deleteSystableData(String tableCode,String[] ids);
	/**
	 * 科室/人员维护页面附属信息保存
	 * @param foreignId 关联外键
	 * @param snapCode  
	 * @param orgCode
	 * @param gridAllDatas  JSON数据
	 */
	public void saveHrSubData(String foreignId,String snapCode,Object gridAllDatas);
	/**
	 * 
	 * @param updateId 更新的外键关联ID
	 * @param tableCode 表名
	 * @param gridAllDatas json数据
	 */
	public void batchEditSave(String[] ids,String tableCode,String gridAllDatas);
	/**
	 * 
	 * @param snapIds 主键ID
	 * @param orgCodes 单位
	 * @param keyList 列名
	 * @param newValueList 新值
	 * @param operPerson 操作人
	 * @param operTime 操作时间
	 */
	public void batchEditPersonSave(List<String> snapIds,List<String> orgCodes,List<String> keyList,List<String> newValueList,Person operPerson, Date operTime);
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
	public void saveTableStructure(SysTableStructure sysTableStructure,Boolean isEntityIsNew,Person person);
	public void deleteTableStructure(String[] ida,String[] fieldInfoIda);
}