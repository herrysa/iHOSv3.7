package com.huge.service;

import java.util.List;
import java.util.Map;

import com.huge.ihos.hql.HqlUtil;
import com.huge.webapp.util.PropertyFilter;

public interface UtilOptService {

	public List<Map<String, String>> getByFilters(String entityName , List<PropertyFilter> filters);
	
	public List<Map<String, String>> getByHqlUtil(HqlUtil hqlUtil);
	
	public List getByHql( String hql );
	/**
	 * 执行sql,返回list
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getBySqlToMap(String sql);
}
