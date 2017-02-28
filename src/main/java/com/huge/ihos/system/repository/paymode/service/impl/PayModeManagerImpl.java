package com.huge.ihos.system.repository.paymode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.paymode.dao.PayModeDao;
import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.ihos.system.repository.paymode.service.PayModeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("payModeManager")
public class PayModeManagerImpl extends GenericManagerImpl<PayMode, String>
		implements PayModeManager {
	private PayModeDao payModeDao;

	@Autowired
	public PayModeManagerImpl(PayModeDao payModeDao) {
		super(payModeDao);
		this.payModeDao = payModeDao;
	}

	public JQueryPager getPayModeCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		return payModeDao.getPayModeCriteria(paginatedList, filters);
	}
}