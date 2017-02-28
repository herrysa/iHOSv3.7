package com.huge.ihos.system.repository.paymode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.paymode.dao.PayConditionDao;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.paymode.service.PayConditionManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("payConditionManager")
public class PayConditionManagerImpl extends
		GenericManagerImpl<PayCondition, String> implements PayConditionManager {
	private PayConditionDao payConditionDao;

	@Autowired
	public PayConditionManagerImpl(PayConditionDao payConditionDao) {
		super(payConditionDao);
		this.payConditionDao = payConditionDao;
	}

	public JQueryPager getPayConditionCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		return payConditionDao.getPayConditionCriteria(paginatedList, filters);
	}
}