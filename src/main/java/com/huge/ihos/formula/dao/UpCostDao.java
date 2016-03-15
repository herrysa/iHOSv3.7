package com.huge.ihos.formula.dao;


import java.util.List;

import com.huge.ihos.formula.model.UpCost;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the UpCost table.
 */
public interface UpCostDao extends GenericDao<UpCost, Long> {
	public JQueryPager getUpCostCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<UpCost> getBycheckPeriodAndDept(String checkPeriod,String dept,Long upItemId,Integer state);
	public List<UpCost> getBycheckPeriodAndOperator(String checkPeriod,String operator,Long upItemId);
}