package com.huge.ihos.system.excelTemplate.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.excelTemplate.model.ExcelTemplate;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ExcelTemplate table.
 */
public interface ExcelTemplateDao extends GenericDao<ExcelTemplate, String> {
	public JQueryPager getExcelTemplateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}