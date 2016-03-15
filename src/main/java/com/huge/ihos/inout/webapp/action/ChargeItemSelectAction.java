package com.huge.ihos.inout.webapp.action;

import java.util.List;

import com.huge.ihos.inout.service.ChargeItemManager;
import com.huge.webapp.action.BaseAction;

public class ChargeItemSelectAction
    extends BaseAction {
    private ChargeItemManager chargeItemManager;

    private List allChargeItemByTypeList;

    public ChargeItemManager getChargeItemManager() {
        return chargeItemManager;
    }

    public void setChargeItemManager( ChargeItemManager chargeItemManager ) {
        this.chargeItemManager = chargeItemManager;
    }

    public List getAllChargeItemByTypeList() {
        return allChargeItemByTypeList;
    }

    public void setAllChargeItemByTypeList( List allChargeItemByTypeList ) {
        this.allChargeItemByTypeList = allChargeItemByTypeList;
    }

    public String chargeItemByTypeSelect() {
        return null;
    }
}
