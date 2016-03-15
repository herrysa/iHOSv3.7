package com.huge.ihos.accounting.voucherType.service;


import java.util.List;
import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VoucherTypeManager extends GenericManager<VoucherType, String> {
     public JQueryPager getVoucherTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}