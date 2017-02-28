package com.huge.ihos.material.service;

import java.util.List;

import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvBalanceBatchManager extends GenericManager<InvBalanceBatch, String> {
	public JQueryPager getInvBalanceBatchCriteria(final JQueryPager paginatedList, List<PropertyFilter> filters);
	public InvBalanceBatch getInvBalanceBatchsByStoreAndBatchAndInv(String orgCode,String copyCode,String yearMonth,String storeId,String invDictId,String batchId);
	/**
	 * 获取对应单位、帐套、期间对应仓库对应材料的库存
	 * @param orgCode 单位
	 * @param copyCode 帐套
	 * @param yearMonth 期间
	 * @param storeId 仓库ID
	 * @param invDictId 材料ID
	 * @return
	 */
	public Double getStoreCurAmount(String orgCode,String copyCode,String yearMonth,String storeId,String invDictId);
	/**
	 * 获取对应单位、帐套、期间对应材料的所有库存，不区分仓库
	 * @param orgCode 单位
	 * @param copyCode 帐套
	 * @param yearMonth 期间
	 * @param invDictId 材料ID
	 * @return
	 */
	public Double getAllStoreCurAmount(String orgCode,String copyCode,String yearMonth,String invDictId);
}