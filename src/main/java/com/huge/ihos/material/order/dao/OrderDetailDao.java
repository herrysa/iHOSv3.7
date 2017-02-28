package com.huge.ihos.material.order.dao;


import java.util.List;

import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the OrderDetail table.
 */
public interface OrderDetailDao extends GenericDao<OrderDetail, String> {
	public JQueryPager getOrderDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}