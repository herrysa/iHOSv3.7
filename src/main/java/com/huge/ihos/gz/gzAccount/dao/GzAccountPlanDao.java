package com.huge.ihos.gz.gzAccount.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzAccountPlan table.
 */
public interface GzAccountPlanDao extends GenericDao<GzAccountPlan, String> {
	public JQueryPager getGzAccountPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Map<String,Object>> getGzAccountGridData(String columns,List<String> sqlFilterList,GzAccountDefine gzAccountDefine,Map<String,String> groupFilterMap) throws Exception;
	public List<Map<String,Object>> getGzAccountReverseGridData(List<GzItem> items,List<String> sqlFilterList,GzAccountDefine gzAccountDefine) throws Exception;
}