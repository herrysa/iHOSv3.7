package com.huge.ihos.material.order.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.order.model.Order;
import com.huge.ihos.material.order.dao.OrderDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("orderDao")
public class OrderDaoHibernate extends GenericDaoHibernate<Order, String> implements OrderDao {

    public OrderDaoHibernate() {
        super(Order.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getOrderCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("orderId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Order.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getOrderCriteria", e);
			return paginatedList;
		}

	}
}
