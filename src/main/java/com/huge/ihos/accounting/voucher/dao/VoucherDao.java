package com.huge.ihos.accounting.voucher.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Voucher table.
 */
public interface VoucherDao extends GenericDao<Voucher, String> {
	public JQueryPager getVoucherCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Voucher> getBysSysVariable(SystemVariable sysVariable,String voucherType,Integer voucherNo);
	
	public List<Map<String, String>> getAccountCollect(Map<String, String> getParams);
	
	public List<Voucher> getByState(SystemVariable sysVariable,Integer state,String type);
	
	public List<Map<String, String>> getAccountCollectBalance(Map<String, String> getParams);
	
	public List<Map<String, String>> getAccountBalance(JQueryPager pagedRequests,Map<String, String> getParams);
	
	public List<Map<String, String>> getAccountBalanceDetail(JQueryPager pagedRequests,Map<String, String> getParams);
		
}