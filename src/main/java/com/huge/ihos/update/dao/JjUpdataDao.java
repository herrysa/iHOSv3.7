package com.huge.ihos.update.dao;


import java.util.List;

import com.huge.ihos.update.model.JjUpdata;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the JjUpdata table.
 */
public interface JjUpdataDao extends GenericDao<JjUpdata, Long> {
	public JQueryPager getJjUpdataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	

	public boolean isHaveUpdateRight(String personId);
	
	public boolean isUpdataed(String checkPeriod,String deptId);
	
	public boolean isExistUpdataed(String checkPeriod, String deptId);
	
	public List<JjUpdata> findByDept(String checkPeriod , String deptId);

	public void executeInheritSql(String sql);
}