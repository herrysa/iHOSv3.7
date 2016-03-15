package com.huge.ihos.accounting.account.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Account table.
 */
public interface AccountDao extends GenericDao<Account, String> {
	public JQueryPager getAccountCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public Account getAccountByNumber(int number,String selectId);
	public void updateAccountDisabled(String acctId,String lossDirection);
	public int hasChildren(String parentId);
	public List<Account> getAll(HashMap<String, String> environment);
	public List<Account> getAllAccountByOCK(String orgCode, String copyCode,
			String kjYear);
}