package com.huge.ihos.bm.budgetModel.dao;


import java.util.List;

import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmModelProcessLog table.
 */
public interface BmModelProcessLogDao extends GenericDao<BmModelProcessLog, String> {
	public JQueryPager getBmModelProcessLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}