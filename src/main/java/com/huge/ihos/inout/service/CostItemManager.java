package com.huge.ihos.inout.service;

import com.huge.ihos.inout.model.CostItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface CostItemManager
    extends GenericManager<CostItem, String> {
    public JQueryPager getCostItemCriteria( final JQueryPager paginatedList );
    /**
     * 检查成本项目是否正在被使用
     * @param costItemId
     * @return true:正在被使用;false：没有被使用
     */
	public boolean isInUse(String costItemId);
	/**
	 * 检查是否有子级
	 * @param costItemId
	 * @return true:非末级;false：末级
	 */
	public boolean hasChildren(String costItemId);
}