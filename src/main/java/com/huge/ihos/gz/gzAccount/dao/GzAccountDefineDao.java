package com.huge.ihos.gz.gzAccount.dao;


import java.util.List;

import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzAccountDefine table.
 */
public interface GzAccountDefineDao extends GenericDao<GzAccountDefine, String> {
	public JQueryPager getGzAccountDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}