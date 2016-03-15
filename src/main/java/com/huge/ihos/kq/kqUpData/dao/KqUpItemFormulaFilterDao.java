package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;

import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqUpItemFormulaFilter table.
 */
public interface KqUpItemFormulaFilterDao extends GenericDao<KqUpItemFormulaFilter, String> {
	public JQueryPager getKqUpItemFormulaFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void saveKqUpItemFormulaFilterGridData(String kqUpItemFormulaJsonStr,String formulaId);
}