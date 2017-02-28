package com.huge.ihos.material.order.service;


import java.util.List;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface OrderDetailManager extends GenericManager<OrderDetail, String> {
     public JQueryPager getOrderDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}