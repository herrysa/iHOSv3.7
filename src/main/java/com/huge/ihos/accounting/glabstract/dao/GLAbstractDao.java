package com.huge.ihos.accounting.glabstract.dao;


import java.util.List;

import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GLAbstract table.
 */
public interface GLAbstractDao extends GenericDao<GLAbstract, String> {
	public JQueryPager getGLAbstractCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}