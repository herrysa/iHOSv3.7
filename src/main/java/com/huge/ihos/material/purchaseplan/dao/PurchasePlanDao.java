package com.huge.ihos.material.purchaseplan.dao;


import java.util.List;

import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PurchasePlan table.
 */
public interface PurchasePlanDao extends GenericDao<PurchasePlan, String> {
	public JQueryPager getPurchasePlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}