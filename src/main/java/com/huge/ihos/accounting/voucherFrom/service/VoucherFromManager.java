package com.huge.ihos.accounting.voucherFrom.service;


import java.util.List;
import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VoucherFromManager extends GenericManager<VoucherFrom, String> {
     public JQueryPager getVoucherFromCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}