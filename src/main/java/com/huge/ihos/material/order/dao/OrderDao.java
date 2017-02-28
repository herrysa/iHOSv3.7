package com.huge.ihos.material.order.dao;


import java.util.List;

import com.huge.ihos.material.order.model.Order;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Order table.
 */
public interface OrderDao extends GenericDao<Order, String> {
	public JQueryPager getOrderCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}