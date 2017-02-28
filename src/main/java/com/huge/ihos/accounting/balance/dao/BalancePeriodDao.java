package com.huge.ihos.accounting.balance.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BalancePeriod table.
 */
public interface BalancePeriodDao extends GenericDao<BalancePeriod, String> {
	public JQueryPager getBalancePeriodCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<BalancePeriod> getBalancePeriodByEnvironment(HashMap<String, String> environment);

	public void updateMonthBalance(String orgCode, String copyCode,
			String kjYear, String month);
}