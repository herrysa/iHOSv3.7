package com.huge.ihos.update.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.update.model.JjDeptMap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the JjDeptMap table.
 */
public interface JjDeptMapDao extends GenericDao<JjDeptMap, Integer> {
	public JQueryPager getJjDeptMapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 根据操作员ID得到部门列表
	 * @param personId
	 * @return
	 */
	public List<Department> getByOperatorId(String personId);
	/**
	 * 根据人员ID获取部门IDS
	 * @param personId
	 * @return
	 */
	public String[] getByDeptIds(String personId);
}