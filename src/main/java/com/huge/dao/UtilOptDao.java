package com.huge.dao;

import java.util.List;
import java.util.Map;

import com.huge.ihos.hql.HqlUtil;
import com.huge.webapp.util.PropertyFilter;

public interface UtilOptDao {

	public List<Map<String, String>> getByFilters(String entityName , List<PropertyFilter> filters);
	
	public List<Map<String, String>> getByHqlUtil(HqlUtil hqlUtil);
	
	List getByHql(String hql);
	public List<Map<String, Object>> getBySqlToMap(String sql);
}
