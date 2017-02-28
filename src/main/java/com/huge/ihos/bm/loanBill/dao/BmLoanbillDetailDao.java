package com.huge.ihos.bm.loanBill.dao;


import java.util.List;

import com.huge.ihos.bm.loanBill.model.BmLoanbillDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmLoanbillDetail table.
 */
public interface BmLoanbillDetailDao extends GenericDao<BmLoanbillDetail, String> {
	public JQueryPager getBmLoanbillDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}