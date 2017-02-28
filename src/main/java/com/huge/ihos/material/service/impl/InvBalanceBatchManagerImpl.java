package com.huge.ihos.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InvBalanceBatchDao;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.service.InvBalanceBatchManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invBalanceBatchManager")
public class InvBalanceBatchManagerImpl extends GenericManagerImpl<InvBalanceBatch, String> implements InvBalanceBatchManager {
	private InvBalanceBatchDao invBalanceBatchDao;

	@Autowired
	public InvBalanceBatchManagerImpl(InvBalanceBatchDao invBalanceBatchDao) {
		super(invBalanceBatchDao);
		this.invBalanceBatchDao = invBalanceBatchDao;
	}

	public JQueryPager getInvBalanceBatchCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		return invBalanceBatchDao.getInvBalanceBatchCriteria(paginatedList,
				filters);
	}

	@Override
	public InvBalanceBatch getInvBalanceBatchsByStoreAndBatchAndInv(String orgCode,String copyCode,String yearMonth,
			String storeId, String invDictId, String batchId) {
		return invBalanceBatchDao.getBatchBalance(orgCode, copyCode, yearMonth, storeId, invDictId, batchId);
	}

	@Override
	public Double getStoreCurAmount(String orgCode, String copyCode,String yearMonth, String storeId, String invDictId) {
		return invBalanceBatchDao.getSumCurAmount(orgCode,copyCode,yearMonth,storeId,invDictId);
	}

	@Override
	public Double getAllStoreCurAmount(String orgCode, String copyCode,String yearMonth, String invDictId) {
		return invBalanceBatchDao.getSumCurAmount(orgCode,copyCode,yearMonth,null,invDictId);
	}
	
}