package com.huge.ihos.system.configuration.funcDefine.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.funcDefine.model.FuncDefine;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the FuncDefine table.
 */
public interface FuncDefineDao extends GenericDao<FuncDefine, String> {
	public JQueryPager getFuncDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}