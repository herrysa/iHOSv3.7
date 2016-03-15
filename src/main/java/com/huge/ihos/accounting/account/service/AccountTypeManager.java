package com.huge.ihos.accounting.account.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface AccountTypeManager extends GenericManager<AccountType, String> {
     public JQueryPager getAccountTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<AccountType> getAll(HashMap<String,String> environment);
     public List<AccountType> getAllAccountTypeByOC(String orgCode,String copyCode);
}