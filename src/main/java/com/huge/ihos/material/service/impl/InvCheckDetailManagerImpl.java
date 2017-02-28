package com.huge.ihos.material.service.impl;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InvBalanceBatchDao;
import com.huge.ihos.material.dao.InvCheckDetailDao;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.service.InvCheckDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invCheckDetailManager")
public class InvCheckDetailManagerImpl extends GenericManagerImpl<InvCheckDetail, String> implements InvCheckDetailManager {
    private InvCheckDetailDao invCheckDetailDao;
    private InvBalanceBatchDao invBalanceBatchDao;
    
    @Autowired
    public void setInvBalanceBatchDao(InvBalanceBatchDao invBalanceBatchDao) {
		this.invBalanceBatchDao = invBalanceBatchDao;
	}

	@Autowired
    public InvCheckDetailManagerImpl(InvCheckDetailDao invCheckDetailDao) {
        super(invCheckDetailDao);
        this.invCheckDetailDao = invCheckDetailDao;
    }
    
    public JQueryPager getInvCheckDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invCheckDetailDao.getInvCheckDetailCriteria(paginatedList,filters);
	}

	@Override
	public List<InvCheckDetail> getInvCheckDetailsInSameCheck(InvCheck invCheck) {
		return invCheckDetailDao.getInvCheckDetailsInSameCheck(invCheck);
	}

	@Override
	public void addInvDictToCheckDetail(String batchIds, InvCheck invCheck) {
		StringTokenizer ids = new StringTokenizer(batchIds, ",");
		String balanceBatchId = "";
		InventoryDict invDict = null;
		InvBalanceBatch invBalanceBatch = null;
		InvCheckDetail invCheckDetail = null;
		while (ids.hasMoreTokens()) {
			balanceBatchId = ids.nextToken();
			invBalanceBatch = invBalanceBatchDao.get(balanceBatchId);
			invDict = invBalanceBatch.getInvDict();
			invCheckDetail = new InvCheckDetail();
			invCheckDetail.setInvDict(invDict);
			invCheckDetail.setInvCheck(invCheck);
			invCheckDetail.setInvBatch(invBalanceBatch.getInvBatch());
			invCheckDetail.setPrice(invBalanceBatch.getPrice());
			invCheckDetail.setAcctAmount(invBalanceBatch.getCurAmount());
			invCheckDetailDao.save(invCheckDetail);
		}
	}
	
	
    
    
}