package com.huge.ihos.gz.gzItemFormula.dao;


import java.util.List;

import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzItemFormula table.
 */
public interface GzItemFormulaDao extends GenericDao<GzItemFormula, String> {
	public JQueryPager getGzItemFormulaCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}