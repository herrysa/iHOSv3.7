package com.huge.ihos.system.repository.paymode.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.repository.paymode.dao.PayConditionDao;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("payConditionDao")
public class PayConditionDaoHibernate extends
		GenericDaoHibernate<PayCondition, String> implements PayConditionDao {

	public PayConditionDaoHibernate() {
		super(PayCondition.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getPayConditionCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("payConId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, PayCondition.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPayConditionCriteria", e);
			return paginatedList;
		}

	}
}
