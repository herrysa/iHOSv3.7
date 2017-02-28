package com.huge.ihos.material.order.service.impl;

import java.util.List;
import com.huge.ihos.material.order.dao.OrderDetailDao;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.service.OrderDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("orderDetailManager")
public class OrderDetailManagerImpl extends GenericManagerImpl<OrderDetail, String> implements OrderDetailManager {
    private OrderDetailDao orderDetailDao;

    @Autowired
    public OrderDetailManagerImpl(OrderDetailDao orderDetailDao) {
        super(orderDetailDao);
        this.orderDetailDao = orderDetailDao;
    }
    
    public JQueryPager getOrderDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return orderDetailDao.getOrderDetailCriteria(paginatedList,filters);
	}
}