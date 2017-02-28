package com.huge.ihos.bm.budgetModel.dao;


import java.util.List;

import com.huge.ihos.bm.budgetModel.model.BmModelAssist;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmModelAssist table.
 */
public interface BmModelAssistDao extends GenericDao<BmModelAssist, String> {
	public JQueryPager getBmModelAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}