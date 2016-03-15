package com.huge.ihos.gz.gzItemFormula.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzItemFormulaFilter table.
 */
public interface GzItemFormulaFilterDao extends GenericDao<GzItemFormulaFilter, String> {
	public JQueryPager getGzItemFormulaFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 保存json串
	 * @param gzItemFormulaJsonStr
	 * @param formulaId
	 */
	public void saveGzItemFormulaFilterGridData(String gzItemFormulaJsonStr,String formulaId);
}