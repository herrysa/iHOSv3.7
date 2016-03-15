package com.huge.ihos.accounting.voucher.service;


import java.util.List;
import com.huge.ihos.accounting.voucher.model.VoucherDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VoucherDetailManager extends GenericManager<VoucherDetail, String> {
     public JQueryPager getVoucherDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public void deleteByVoucherId(String voucherId);
}