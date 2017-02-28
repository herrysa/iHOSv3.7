package com.huge.ihos.accounting.account.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.account.model.ProjAccount;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ProjAccount table.
 */
public interface ProjAccountDao extends GenericDao<ProjAccount, String> {
	public JQueryPager getProjAccountCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}