package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvBalanceBatch table.
 */
public interface InvBalanceBatchDao extends GenericDao<InvBalanceBatch, String> {
	public JQueryPager getInvBalanceBatchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void updateBatchBalance(InvMain im,InvDetail idl,String type);
	public InvBalanceBatch getBatchBalance(String orgCode, String copyCode,String yearMonth, String storeId, String invDicId, String batchNo);
	public void delete(String storeId,String orgCode,String copyCode,String kjYear);
	public Double getSumCurAmount(String orgCode, String copyCode,String yearMonth, String storeId, String invDictId);
}