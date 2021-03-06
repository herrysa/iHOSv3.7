package com.huge.dao;

import java.util.List;
import java.util.Map;

import com.huge.ihos.hql.HqlUtil;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface UtilOptDao {

	public List<Map<String, String>> getByFilters(String entityName , List<PropertyFilter> filters);
	
	public List<Map<String, String>> getByHqlUtil(HqlUtil hqlUtil);
	
	List getByHql(String hql);
	public List<Map<String, Object>> getBySqlToMap(String sql);
	
	public JQueryPager getBdInfoCriteriaWithSearch( final JQueryPager paginatedList, final BdInfoUtil bdInfoUtil, List<PropertyFilter> filters );

	/**
     * 执行原生态Sql语句
     * @param sql
     * @return 受影响的行数
     */
	int excuteSql(String sql);
}
