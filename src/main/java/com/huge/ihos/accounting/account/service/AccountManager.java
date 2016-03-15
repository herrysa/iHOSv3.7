package com.huge.ihos.accounting.account.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface AccountManager extends GenericManager<Account, String> {
     public JQueryPager getAccountCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public Account getAccountByNumber(int number,String selectId);
	 public void updateAccountDisabled(String acctId,String lossDirection);
	 /**
	  * retrun -1：不存在；0:没孩子；1：有孩子
	  */
	 public int hasChildren(String parentId);
	 public Account save(Account account,String codeRule);
	 
	 public List<Account> getAll(HashMap<String,String> environment);
	 public List<Account> getAllAccountByOCK(String orgCode,String copyCode,String kjYear);
	public void initAccount(HashMap<String, String> environment);
}