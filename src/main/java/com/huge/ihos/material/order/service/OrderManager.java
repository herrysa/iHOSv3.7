package com.huge.ihos.material.order.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.material.order.model.Order;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface OrderManager extends GenericManager<Order, String> {
     public JQueryPager getOrderCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void stopOrder(List<String> stopIds);

	 public void antiAuditOrder(List<String> cancelIds, Person person);

	 public void auditOrder(List<String> checkIds, Person person,Date date);

	 public Order saveOrder(Order order, OrderDetail[] orderDetails);

	 public Order extendOrder(List<String> purchaseIds,Person person);

	 public Order unionOrder(List<String> unionIds,Person person);

	 public void remove(String removeId);
	 
	 public void removeOrIdInPurchasePlan(String removeId);
}