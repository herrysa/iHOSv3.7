package com.huge.ihos.system.repository.paymode.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.repository.paymode.dao.PayModeDao;
import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("payModeDao")
public class PayModeDaoHibernate extends GenericDaoHibernate<PayMode, String>
		implements PayModeDao {

	public PayModeDaoHibernate() {
		super(PayMode.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getPayModeCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("payModeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, PayMode.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPayModeCriteria", e);
			return paginatedList;
		}

	}
}
