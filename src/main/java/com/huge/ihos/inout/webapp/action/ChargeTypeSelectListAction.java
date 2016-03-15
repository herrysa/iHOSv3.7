package com.huge.ihos.inout.webapp.action;

import java.util.List;

import com.huge.ihos.inout.service.ChargeItemManager;
import com.huge.ihos.inout.service.ChargeTypeManager;
import com.huge.webapp.action.BaseAction;

public class ChargeTypeSelectListAction
    extends BaseAction {
    private ChargeTypeManager chargeTypeManager;

    private List allChargeTypeList;

    public ChargeTypeManager getChargeTypeManager() {
        return chargeTypeManager;
    }

    public void setChargeTypeManager( ChargeTypeManager chargeTypeManager ) {
        this.chargeTypeManager = chargeTypeManager;
    }

    public List getAllChargeTypeList() {
        return allChargeTypeList;
    }

    public void setAllChargeTypeList( List allChargeTypeList ) {
        this.allChargeTypeList = allChargeTypeList;
    }

    public String allChargeTypeSelectList() {
        this.allChargeTypeList = this.chargeTypeManager.getAllChargeTypeList();
        return SUCCESS;
    }

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
}
