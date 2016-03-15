package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqMonthData table.
 */
public interface KqMonthDataDao extends GenericDao<KqMonthData, String> {
	public JQueryPager getKqMonthDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Map<String,Object>> getKqMonthDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);
	
	/**
	 * 
	 */
	public List<KqMonthData> getKqDeptCheckDatas();
}