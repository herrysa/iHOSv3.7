package com.huge.ihos.material.purchaseplan.dao;


import java.util.List;

import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PurchasePlanDetail table.
 */
public interface PurchasePlanDetailDao extends GenericDao<PurchasePlanDetail, String> {
	public JQueryPager getPurchasePlanDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}