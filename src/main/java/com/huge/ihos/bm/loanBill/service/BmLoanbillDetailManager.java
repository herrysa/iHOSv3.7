package com.huge.ihos.bm.loanBill.service;


import java.util.List;
import com.huge.ihos.bm.loanBill.model.BmLoanbillDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmLoanbillDetailManager extends GenericManager<BmLoanbillDetail, String> {
     public JQueryPager getBmLoanbillDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}