package com.huge.ihos.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InvBatchDao;
import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.material.service.InvBatchManager;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("invBatchManager")
public class InvBatchManagerImpl extends GenericManagerImpl<InvBatch, String> implements InvBatchManager {
    private InvBatchDao invBatchDao;

    @Autowired
    public InvBatchManagerImpl(InvBatchDao invBatchDao) {
        super(invBatchDao);
        this.invBatchDao = invBatchDao;
    }
    
    //TODO 此方法还需要在完善，定义条码规则后由生成器生成
    public String getNextPerBar(String batchId){
    	
    	InvBatch ib = this.invBatchDao.get(batchId);//InvBatchByDictAndBatchNo(invId, batchNo);
    	
    	Integer sn = ib.getBatchSerial();
    	
    	ib.setBatchSerial(sn+1);
    	this.save(ib);
    	
    	
    	
    	
    	return ib.getBatchNo()+sn;
    }
    
    
    public JQueryPager getInvBatchCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invBatchDao.getInvBatchCriteria(paginatedList,filters);
	}

	@Override
	public boolean isDoubleBatchNo(String invDictId, String batchNo,
			String orgCode, String copyCode,Store store) {
		return !(invBatchDao.getInvBatchByDictAndBatchNo(invDictId, batchNo, orgCode, copyCode,store)==null);
	}
    
    
}