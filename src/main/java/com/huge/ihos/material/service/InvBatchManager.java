package com.huge.ihos.material.service;


import java.util.List;

import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvBatchManager extends GenericManager<InvBatch, String> {
     public JQueryPager getInvBatchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public String getNextPerBar(String batchId);//,String batchNo);
     public boolean isDoubleBatchNo(String invDictId,String batchNo,String orgCode,String copyCode,Store store);
}