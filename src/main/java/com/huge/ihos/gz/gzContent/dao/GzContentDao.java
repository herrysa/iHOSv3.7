package com.huge.ihos.gz.gzContent.dao;


import java.util.List;
import java.util.Map;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzContent table.
 */
public interface GzContentDao extends GenericDao<GzContent, String> {
	public JQueryPager getGzContentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	public List<Map<String,Object>> getGzContentGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);

	public Map<String, Integer> getPersonCountMap(String id,String currPeriod,String gzTypeId);
}