package com.huge.ihos.system.repository.bank.service;


import java.util.List;

import com.huge.ihos.system.repository.bank.model.Bank;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BankManager extends GenericManager<Bank, String> {
     public JQueryPager getBankCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}