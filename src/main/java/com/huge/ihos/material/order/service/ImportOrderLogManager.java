package com.huge.ihos.material.order.service;

import java.util.List;

import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.order.model.ImportOrderLog;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ImportOrderLogManager extends GenericManager<ImportOrderLog, String> {
    public JQueryPager getImportOrderLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
    /**
     * 回写、更新和删除 根据订单入库的log
     * @param invMain 入库单
     * @param fromOrderData 
     * @param type ["save","confirm","delete"]
     */
	public void setImportLog(InvMain invMain, String fromOrderData,String type,Person person);
	/**
	 * 通过订单id和材料Id获取该材料已经入库的数量
	 * @param orderId
	 * @param invId
	 * @return
	 */
	public Double getInAmountFromImportOrderLog(String orderId,String invId);
}