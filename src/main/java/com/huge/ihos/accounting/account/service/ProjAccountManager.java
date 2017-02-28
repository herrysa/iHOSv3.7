package com.huge.ihos.accounting.account.service;


import java.util.List;

import com.huge.ihos.accounting.account.model.ProjAccount;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProjAccountManager extends GenericManager<ProjAccount, String> {
     public JQueryPager getProjAccountCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}