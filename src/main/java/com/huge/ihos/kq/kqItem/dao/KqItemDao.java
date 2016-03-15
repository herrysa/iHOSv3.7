package com.huge.ihos.kq.kqItem.dao;


import java.util.List;

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqItem table.
 */
public interface KqItemDao extends GenericDao<KqItem, String> {
	public JQueryPager getKqItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}