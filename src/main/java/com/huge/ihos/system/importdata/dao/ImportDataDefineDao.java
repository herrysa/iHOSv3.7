package com.huge.ihos.system.importdata.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ImportDataDefine table.
 */
public interface ImportDataDefineDao extends GenericDao<ImportDataDefine, String> {
	public JQueryPager getImportDataDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public ImportDataDefine findById(String interfaceId);
}