package com.huge.ihos.accounting.AssistType.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the AssistType table.
 */
public interface AssistTypeDao extends GenericDao<AssistType, String> {
	public JQueryPager getAssistTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<HashMap<String ,String >> getAssits(String table, String assitMark, String id_name, String id_value, List<AssistType> assitTypes);
}