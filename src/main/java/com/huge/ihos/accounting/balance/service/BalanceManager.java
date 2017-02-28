package com.huge.ihos.accounting.balance.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BalanceManager extends GenericManager<Balance, String> {
     public JQueryPager getBalanceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public JQueryPager checkBalance(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public JQueryPager checkBalance(final JQueryPager paginatedList,List<PropertyFilter> filters, String assitBalance);
     public List<Balance> getBalancesByAcct(String acctId);
     public List<AssistType> getAssistTypeByAcct(String acctId);
     public Account getAcctById(String acctId);
     public Boolean editBalance(Balance balance,Map<String, Double> diffMap);
	 public List<Balance> getBalWithAssistByAcct(String acctId);
	 public List<HashMap<String ,String>> isBalance(HashMap<String, String> environment);
	 public Boolean init(HashMap<String, String> environment);
	public List<Balance> getAll(HashMap<String, String> environment);
}