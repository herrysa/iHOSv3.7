package com.huge.webapp.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.service.PinYinCodeSelectManager;

public class PinYinCodeSelectAction
    extends BaseAction {
    private String pinYinCode;

    private String term;

    private String chargeTypeSelect;

    public String getChargeTypeSelect() {
        return chargeTypeSelect;
    }

    public void setChargeTypeSelect( String chargeTypeSelect ) {
        this.chargeTypeSelect = chargeTypeSelect;
    }

    private Sourcepayin sourcepayin;

    public Sourcepayin getSourcepayin() {
        return sourcepayin;
    }

    public void setSourcepayin( Sourcepayin sourcepayin ) {
        this.sourcepayin = sourcepayin;
    }

    private PinYinCodeSelectManager pinYinCodeSelectManager;

    public PinYinCodeSelectManager getPinYinCodeSelectManager() {
        return pinYinCodeSelectManager;
    }

    public void setPinYinCodeSelectManager( PinYinCodeSelectManager pinYinCodeSelectManager ) {
        this.pinYinCodeSelectManager = pinYinCodeSelectManager;
    }

    public String getPinYinCode() {
        return pinYinCode;
    }

    public void setPinYinCode( String pinYinCode ) {
        this.pinYinCode = pinYinCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm( String term ) {
        this.term = term;
    }

    private Map dicList;

    public Map getDicList() {
        return dicList;
    }

    /*
     * public void setDicPYList(Map dicPYList) { this.dicPYList = dicPYList; }
     */

    public String dictionaryPinYinSelect() {
        this.dicList = this.pinYinCodeSelectManager.getDictionaryByPinYinCode( term );
        return this.SUCCESS;
    }

    private Map deptList;

    public Map getDeptList() {
        return deptList;
    }

    public String deptPinYinSelect() {
        this.deptList = this.pinYinCodeSelectManager.getDepartmentByPinYinCode( term );
        return this.SUCCESS;
    }

    private Map deptAllList;

    public Map getDeptAllList() {
        return deptAllList;
    }

    public String deptAllPinYinSelect() {
        this.deptAllList = this.pinYinCodeSelectManager.getAllDepartmentByPinYinCode( term );
        return this.SUCCESS;
    }

    private Map personList;

    public Map getPersonList() {
        return personList;
    }

    public String personPinYinSelect() {
        this.personList = this.pinYinCodeSelectManager.getPersonByPinYinCode( term );
        return this.SUCCESS;
    }

    private Map chargeTypeList;

    private Map chargeItemList;

    private Map costItemList;

    public Map getChargeTypeList() {
        return chargeTypeList;
    }

    public Map getChargeItemList() {
        return chargeItemList;
    }

    public Map getCostItemList() {
        return costItemList;
    }

    public String chargeTypePinYinSelect() {
        this.chargeTypeList = this.pinYinCodeSelectManager.getChargeTypeByPinYinCode( term );
        return this.SUCCESS;
    }

    public String chargeItemPinYinSelect() {
        HttpServletRequest req = this.getRequest();
        String cts = this.getRequest().getParameter( "chargeTypeSelect" );
        this.chargeItemList = this.pinYinCodeSelectManager.getChargeItemByPinYinCodeAndChargeType( term, this.chargeTypeSelect );
        return this.SUCCESS;
    }

    public String costItemPinYinSelect() {
        this.costItemList = this.pinYinCodeSelectManager.getCostItemByPinYinCode( term );
        return this.SUCCESS;
    }

    public Map getFdcMap() {
        Map map = new HashMap();
        map.put( "a", "a" );
        map.put( "b", "b" );
        map.put( "c", "c" );
        return map;
    }
}
