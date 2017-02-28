package com.huge.ihos.bm.loanBill.dao;


import java.util.List;

import com.huge.ihos.bm.loanBill.model.BmLoanbill;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmLoanbill table.
 */
public interface BmLoanbillDao extends GenericDao<BmLoanbill, String> {
	public JQueryPager getBmLoanbillCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}