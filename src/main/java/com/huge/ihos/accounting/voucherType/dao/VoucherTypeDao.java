package com.huge.ihos.accounting.voucherType.dao;


import java.util.List;

import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the VoucherType table.
 */
public interface VoucherTypeDao extends GenericDao<VoucherType, String> {
	public JQueryPager getVoucherTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}