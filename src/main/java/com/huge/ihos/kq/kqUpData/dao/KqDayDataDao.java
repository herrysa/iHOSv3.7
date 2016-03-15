package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqDayData table.
 */
public interface KqDayDataDao extends GenericDao<KqDayData, String> {
	public JQueryPager getKqDayDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Map<String,Object>> getKqDayDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);
	public List<Map<String, Object>> getKqSumaryDayData(String curPeriod,String kqTypeId,String curDeptId);
}