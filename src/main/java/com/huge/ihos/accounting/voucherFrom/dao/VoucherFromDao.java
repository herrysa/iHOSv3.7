package com.huge.ihos.accounting.voucherFrom.dao;


import java.util.List;

import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the VoucherFrom table.
 */
public interface VoucherFromDao extends GenericDao<VoucherFrom, String> {
	public JQueryPager getVoucherFromCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}