package com.huge.ihos.accounting.account.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the AccountType table.
 */
public interface AccountTypeDao extends GenericDao<AccountType, String> {
	public JQueryPager getAccountTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<AccountType> getAll(HashMap<String,String> environment);
	public List<AccountType> getAllAccountTypeByOC(String orgCode, String copyCode);

}