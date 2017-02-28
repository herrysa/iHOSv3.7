package com.huge.ihos.material.order.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.order.model.OrderDetail;
import com.huge.ihos.material.order.dao.OrderDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("orderDetailDao")
public class OrderDetailDaoHibernate extends GenericDaoHibernate<OrderDetail, String> implements OrderDetailDao {

    public OrderDetailDaoHibernate() {
        super(OrderDetail.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getOrderDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("orderDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, OrderDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getOrderDetailCriteria", e);
			return paginatedList;
		}

	}
}
