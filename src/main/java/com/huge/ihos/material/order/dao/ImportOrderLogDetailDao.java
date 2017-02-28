package com.huge.ihos.material.order.dao;


import java.util.List;

import com.huge.ihos.material.order.model.ImportOrderLogDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ImportOrderLogDetail table.
 */
public interface ImportOrderLogDetailDao extends GenericDao<ImportOrderLogDetail, String> {
	public JQueryPager getImportOrderLogDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}