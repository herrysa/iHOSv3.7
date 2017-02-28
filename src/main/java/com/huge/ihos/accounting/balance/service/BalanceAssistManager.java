package com.huge.ihos.accounting.balance.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BalanceAssistManager extends GenericManager<BalanceAssist, String> {
     public JQueryPager getBalanceAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);


	public Boolean removeAssist(String balanceId);
	public Boolean saveBalance(String balanceId, String status);
	public Boolean saveBalanceAssist(String assistBalanceJson, String balanceId);

	public List<HashMap<String, String>> getAssistTypes(Balance balance);

	public List<HashMap<String, Object>> getAssistByBalance(String balanceId);
}