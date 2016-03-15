package com.huge.ihos.gz.gzAccount.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzAccountPlanManager extends GenericManager<GzAccountPlan, String> {
     public JQueryPager getGzAccountPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Map<String,Object>> getGzAccountGridData(String columns,List<String> sqlFilterList,GzAccountDefine gzAccountDefine,Map<String,String> groupFilterMap) throws Exception;
	/**
	 * 反转
	 * @param items
	 * @param sqlFilterList
	 * @param gzAccountDefine
	 * @return
	 */
	public List<Map<String,Object>> getGzAccountReverseGridData(List<GzItem> items,List<String> sqlFilterList,GzAccountDefine gzAccountDefine) throws Exception;
	/**
	 * 保存方案
	 * @param defineId 方案类型
	 * @param gzTypeId 工资类型
	 * @param planName 方案名称
	 * @param toPublic 公共
	 * @param toDepartment 部门
	 * @param toRole 角色
	 * @param gzAccountItemsStr 工资项
	 * @param gzAccountFilterStr 过滤条件
	 * @param userId 角色ID
	 * @return
	 */
	public GzAccountPlan saveGzAccountPlan(String defineId,String gzTypeId,String planName,String toPublic,String toDepartment,String toRole,String gzAccountItemsStr,String gzAccountFilterStr,String userId) throws Exception;
	/**
	 * 删除方案
	 * @param planId
	 */
	public void removeGzAccountPlan(String planId);
}