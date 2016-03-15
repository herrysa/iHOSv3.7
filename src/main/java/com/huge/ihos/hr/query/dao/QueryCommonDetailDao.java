package com.huge.ihos.hr.query.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the QueryCommonDetail table.
 */
public interface QueryCommonDetailDao extends GenericDao<QueryCommonDetail, String> {
	public JQueryPager getQueryCommonDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void saveQueryCommonDetailGridData(String gridAllDatas,String queryCommonId,Person person);
	public String queryHrPersonIds(String queryCommonId,String snapCode);
	/**
	 * 获取人员常用查询Sql
	 * @param
	 * @return
	 */
	public String getQueryCommonSql();
	/**
	 * 获取常用查询结果
	 * @param sql
	 * @param personIdIndex
	 * @param personId
	 * @return
	 */
	public String getQueryCommonResult(String sql,String personId);
	/**
	 * 获取人员常用查询Sql
	 * @param
	 * @return
	 */
	public String getQueryCommonSql(String queryCommonId,String snapCode);
}