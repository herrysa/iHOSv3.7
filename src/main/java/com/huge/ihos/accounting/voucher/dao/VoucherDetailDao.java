package com.huge.ihos.accounting.voucher.dao;


import java.util.List;

import com.huge.ihos.accounting.voucher.model.VoucherDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the VoucherDetail table.
 */
public interface VoucherDetailDao extends GenericDao<VoucherDetail, String> {
	public JQueryPager getVoucherDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public void deleteByVoucherId(String voucherId);
}