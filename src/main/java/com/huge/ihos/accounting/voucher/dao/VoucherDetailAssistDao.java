package com.huge.ihos.accounting.voucher.dao;


import java.util.List;

import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the VoucherDetailAssist table.
 */
public interface VoucherDetailAssistDao extends GenericDao<VoucherDetailAssist, String> {
	public JQueryPager getVoucherDetailAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}