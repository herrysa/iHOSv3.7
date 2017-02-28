package com.huge.ihos.material.dao;


import java.util.Date;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvBalance table.
 */
public interface InvBatchDao extends GenericDao<InvBatch, String> {
	public JQueryPager getInvBatchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	//public void updateBalance(InvMain im,InvDetail idl);
	public InvBatch getInvBatchByDictAndBatchNo(String invDictId, String batchNo,String org,String copy,Store store) ;
	public InvBatch addDefaultBatch(String invDictId,String org,String copy,int vdc) ;
	public InvBatch addBatch(String invid, String org, String copy, String batchNo, Date vd,Store store);
}