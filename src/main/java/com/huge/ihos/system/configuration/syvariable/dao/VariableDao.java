package com.huge.ihos.system.configuration.syvariable.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SyVariable table.
 */
public interface VariableDao extends GenericDao<Variable, String> {
	public JQueryPager getSyVariableCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Variable> getVariableByStatus(Boolean isSys);
	public List<Variable> getEnableVariables();
}