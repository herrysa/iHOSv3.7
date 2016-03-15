package com.huge.dao;

import java.util.Map;

public interface PinYinCodeSelectDao {
    public String testPinYinFunction( String words );

    public Map getDictionaryByPinYinCode( String code );

    public Map getDepartmentByPinYinCode( String code );

    public Map getAllDepartmentByPinYinCode( String code );

    public Map getPersonByPinYinCode( String code );

    public Map getChargeTypeByPinYinCode( String code );

    public Map getChargeItemByPinYinCodeAndChargeType( String code, String chargeType );

    public Map getCostItemByPinYinCode( String code );
}
