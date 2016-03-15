package com.huge.ihos.accounting.voucher.service;


import java.util.List;
import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VoucherDetailAssistManager extends GenericManager<VoucherDetailAssist, String> {
     public JQueryPager getVoucherDetailAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}