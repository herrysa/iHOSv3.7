package com.huge.ihos.system.configuration.colsetting.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.colsetting.model.ColSearch;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ColSearch table.
 */
public interface ColSearchDao extends GenericDao<ColSearch, String> {
	public JQueryPager getColSearchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ColSearch> getByEntityName(String entityName);
	
	public List<ColSearch> getByTemplName(String templName,String entityName,String userId);
	
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId);
}