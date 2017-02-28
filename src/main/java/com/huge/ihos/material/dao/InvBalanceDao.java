package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvBalance;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvBalance table.
 */
public interface InvBalanceDao extends GenericDao<InvBalance, String> {
	public JQueryPager getInvBalanceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void updateBalance(InvMain im,InvDetail idl,String type);
	public void delete(String storeId,String orgCode,String copyCode,String kjYear);
}