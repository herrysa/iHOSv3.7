package com.huge.ihos.kaohe.service;

import java.util.List;

import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.service.BaseTreeManager;

public interface KpiItemManager
    extends BaseTreeManager<KpiItem, Long> {
    // public List processNodeIsLeaf(List list);
    // public void rebuildNestSetTree();
    // public int buildNestTreeFromAdjacency(Long id,int leftNmuber);
    public List<KpiItem> getTreeByRootIdAndPeriodType( Long rootId, String periodType );

    List<KpiItem> getFullTreeByRootId( Long rootId );
    
    
    public void processNodeLeaf();
    
}
