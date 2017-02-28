package com.huge.ihos.accounting.balance.service;


import java.util.HashMap;
import java.util.List;
import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BalancePeriodManager extends GenericManager<BalancePeriod, String> {
     public JQueryPager getBalancePeriodCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<BalancePeriod> getBalancePeriodByEnvironment(HashMap<String, String> environment);

	public void updateMonthBalance(String orgCode, String copyCode,
			String kjYear, String month);
}