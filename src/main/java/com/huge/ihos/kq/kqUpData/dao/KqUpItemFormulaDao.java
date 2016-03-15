package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqUpItemFormula table.
 */
public interface KqUpItemFormulaDao extends GenericDao<KqUpItemFormula, String> {
	public JQueryPager getKqUpItemFormulaCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}