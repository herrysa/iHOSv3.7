package com.huge.ihos.accounting.account.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.account.dao.AccountTypeDao;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.ihos.accounting.account.service.AccountTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("accountTypeManager")
public class AccountTypeManagerImpl extends GenericManagerImpl<AccountType, String> implements AccountTypeManager {
    private AccountTypeDao accountTypeDao;

    @Autowired
    public AccountTypeManagerImpl(AccountTypeDao accountTypeDao) {
        super(accountTypeDao);
        this.accountTypeDao = accountTypeDao;
    }
    
    public JQueryPager getAccountTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return accountTypeDao.getAccountTypeCriteria(paginatedList,filters);
	}

	@Override
	public List<AccountType> getAll(HashMap<String, String> environment) {
		
		return accountTypeDao.getAll(environment);
	}

	@Override
	public List<AccountType> getAllAccountTypeByOC(String orgCode, String copyCode) {
		return accountTypeDao.getAllAccountTypeByOC(orgCode,copyCode);
	}
    
}