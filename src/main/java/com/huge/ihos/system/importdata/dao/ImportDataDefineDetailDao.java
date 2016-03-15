package com.huge.ihos.system.importdata.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ImportDataDefineDetail table.
 */
public interface ImportDataDefineDetailDao extends GenericDao<ImportDataDefineDetail, String> {
	public JQueryPager getImportDataDefineDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}