package com.huge.ihos.system.repository.bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.bank.dao.BankDao;
import com.huge.ihos.system.repository.bank.model.Bank;
import com.huge.ihos.system.repository.bank.service.BankManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bankManager")
public class BankManagerImpl extends GenericManagerImpl<Bank, String> implements BankManager {
    private BankDao bankDao;

    @Autowired
    public BankManagerImpl(BankDao bankDao) {
        super(bankDao);
        this.bankDao = bankDao;
    }
    
    public JQueryPager getBankCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bankDao.getBankCriteria(paginatedList,filters);
	}
}