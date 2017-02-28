package com.huge.ihos.accounting.balance.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BalanceAssist table.
 */
public interface BalanceAssistDao extends GenericDao<BalanceAssist, String> {
	public JQueryPager getBalanceAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<BalanceAssist> getAssistByBalanceId(String balanceId);

	public List<HashMap<String, Object>> getAssistMapByBalanceId(String balanceId);

	public List<HashMap<String,Object>> getAssistGroup(String balanceId);

	public Boolean removeAssist(String balanceId);

	public List<HashMap<String, Object>> getByNumBalance(String balanceId,
			String num);

	public List<HashMap<String, String>> getAssistTypes(String balanceId,
			String typeCode);

	public List<HashMap<String, Object>> getAssistSumByBalance(String balanceId);

}