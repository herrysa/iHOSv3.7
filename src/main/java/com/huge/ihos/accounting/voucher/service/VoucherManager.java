package com.huge.ihos.accounting.voucher.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VoucherManager extends GenericManager<Voucher, String> {
     public JQueryPager getVoucherCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public List<Voucher> getBysSysVariable(SystemVariable sysVariable,String voucherType,Integer voucherNo);
     
     public List<Map<String, String>> getAccountCollect(Map<String, String> getParams);
     
     public List<Voucher> getByState(SystemVariable sysVariable,Integer state,String type);
     
     public List<Map<String, String>> getAccountCollectBalance(Map<String, String> getParams);
     
     public List<Map<String, String>> getAccountBalance(JQueryPager pagedRequests,Map<String, String> getParams);
}