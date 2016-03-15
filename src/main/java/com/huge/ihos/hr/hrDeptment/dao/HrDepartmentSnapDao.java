package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDepartmentSnap table.
 */
public interface HrDepartmentSnapDao extends GenericDao<HrDepartmentSnap, String> {
	public JQueryPager getHrDepartmentSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 根据历史时间获取snapId的集合
	 * @param hisTime
	 * @return
	 */
	public List<String> getHisSnapIdList(String hisTime);
	
	public HrDepartmentSnap getMaxHrDepartmentSnap(String deptId);
	/**
	 * 获取deptId和dept的map
	 * @param hisTime
	 * @return
	 */
	public Map<String,HrDepartmentSnap> getDeptIdDeptMap(String hisTime);
	
	public List<HrDepartmentSnap> getHrDepartmentSnapListBysql(String sql);
}