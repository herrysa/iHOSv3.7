package com.huge.ihos.inout.webapp.action;

import java.util.List;

import com.huge.ihos.inout.service.CostItemManager;
import com.huge.webapp.action.BaseAction;

public class CostItemSelectListAction
    extends BaseAction {
    private CostItemManager costItemManager;

    private List allCostItemList;

    public CostItemManager getCostItemManager() {
        return costItemManager;
    }

    public void setCostItemManager( CostItemManager costItemManager ) {
        this.costItemManager = costItemManager;
    }

    public List getAllCostItemList() {
        return allCostItemList;
    }

    public void setAllCostItemList( List allCostItemList ) {
        this.allCostItemList = allCostItemList;
    }

    public String allCostItemListSelect() {
        /*this.allCostItemList = this.costItemManager.getAll();*/
        return SUCCESS;
    }
}
