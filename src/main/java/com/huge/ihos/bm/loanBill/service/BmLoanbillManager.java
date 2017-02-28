package com.huge.ihos.bm.loanBill.service;


import java.util.List;
import com.huge.ihos.bm.loanBill.model.BmLoanbill;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmLoanbillManager extends GenericManager<BmLoanbill, String> {
     public JQueryPager getBmLoanbillCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}