package com.huge.ihos.formula.dao;


import java.util.List;

import com.huge.ihos.formula.model.UpItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the UpItem table.
 */
public interface UpItemDao extends GenericDao<UpItem, Long> {
	public JQueryPager getUpItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<UpItem> getUpItemsByDept(String deptId,String upItemClass);
	
	public boolean validataUpCost(Long upItemId);
}