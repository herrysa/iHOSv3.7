package com.huge.ihos.hr.hrPerson.dao;


import java.util.List;

import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrPersonCurrent table.
 */
public interface HrPersonCurrentDao extends GenericDao<HrPersonCurrent, String> {
	public JQueryPager getHrPersonCurrentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Object[]> getHrPersonSomeFileds(String ids,String fileds);
	/**
	 * 人员是否可以删除
	 * @param personIds 人员ID字符串
	 * @return
	 */
	public Boolean personRemovable(String personIds);
	/**
	 * 获取当前人员的personId集合
	 * @return
	 */
	public List<String> getPersonIdList();
}