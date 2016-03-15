package com.huge.ihos.kaohe.dao;

import java.util.List;

import com.huge.dao.BaseTreeDao;
import com.huge.ihos.kaohe.model.KpiItem;

/**
 * An interface that provides a data management interface to the DemoTreeNode
 * table.
 */
public interface KpiItemDao extends BaseTreeDao<KpiItem, Long> {
	// public int getChildCount(Long pId);
    public List<KpiItem> getTreeLeafByRootIdAndPeriodType( Long rootId, String periodType );
    public List<KpiItem> getTreeNoLeafByRootIdAndLeafLessThree( Long rootId);
    List<KpiItem> getFullTreeByRootId( Long rootId );
    public void processNodeLeaf();
}
