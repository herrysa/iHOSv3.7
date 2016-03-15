package com.huge.ihos.gz.gzItemPersonType.dao;


import java.util.List;

import com.huge.ihos.gz.gzItemPersonType.model.GzItemPersonType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the gzItemPersonType table.
 */
public interface gzItemPersonTypeDao extends GenericDao<GzItemPersonType, String> {
	public JQueryPager getgzItemPersonTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}