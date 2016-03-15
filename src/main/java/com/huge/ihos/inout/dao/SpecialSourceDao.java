package com.huge.ihos.inout.dao;


import java.util.List;

import com.huge.ihos.inout.model.SpecialSource;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SpecialSource table.
 */
public interface SpecialSourceDao extends GenericDao<SpecialSource, Long> {
	public JQueryPager getSpecialSourceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public String getCBStatus(String checkPeriod);
	public String getItemType(String itemId);
	public List changeSpecialItemList(String itemTypeName);
}