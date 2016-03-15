package com.huge.ihos.system.repository.bank.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.repository.bank.model.Bank;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Bank table.
 */
public interface BankDao extends GenericDao<Bank, String> {
	public JQueryPager getBankCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}