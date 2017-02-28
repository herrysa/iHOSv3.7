package com.huge.ihos.material.purchaseplan.service;


import java.util.List;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PurchasePlanDetailManager extends GenericManager<PurchasePlanDetail, String> {
     public JQueryPager getPurchasePlanDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}