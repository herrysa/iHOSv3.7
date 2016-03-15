package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;

import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqUpItem table.
 */
public interface KqUpItemDao extends GenericDao<KqUpItem, String> {
	public JQueryPager getKqUpItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}