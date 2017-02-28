package com.huge.ihos.accounting.balance.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Balance table.
 */
public interface BalanceDao extends GenericDao<Balance, String> {
	public JQueryPager getBalanceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Balance> getBalancesByAcct(String acctId);
	public List<Balance> getBalWithAssistByAcct(String acctId);
	public List<Balance> getBalancesByAcctCode(String acctCode, String orgCode,String kjYear, String copyCode);
	public List<HashMap<String ,String>> isBalance(HashMap<String, String> environment);
	public List<Balance> getBalanceCriteriaWithCount(JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Balance> getAll(HashMap<String, String> environment);
}