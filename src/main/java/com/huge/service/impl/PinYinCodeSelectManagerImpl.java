package com.huge.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.dao.PinYinCodeSelectDao;
import com.huge.service.PinYinCodeSelectManager;

@Service( "pinYinCodeSelectManager" )
public class PinYinCodeSelectManagerImpl
    implements PinYinCodeSelectManager {
    PinYinCodeSelectDao pinYinCodeSelectDao;

    public PinYinCodeSelectDao getPinYinCodeSelectDao() {
        return pinYinCodeSelectDao;
    }

    @Autowired
    public void setPinYinCodeSelectDao( PinYinCodeSelectDao pinYinCodeSelectDao ) {
        this.pinYinCodeSelectDao = pinYinCodeSelectDao;
    }

    public Map getDictionaryByPinYinCode( String code ) {
        code = code.toUpperCase();
        Map map = this.pinYinCodeSelectDao.getDictionaryByPinYinCode( code );
        //		if(map.size()==0)
        //			map=this.pinYinCodeSelectDao.getDictionaryByHanZiCode(code);
        return map;
    }

    public Map getDepartmentByPinYinCode( String code ) {
        return this.pinYinCodeSelectDao.getDepartmentByPinYinCode( code.toUpperCase().trim() );
    }

    public Map getAllDepartmentByPinYinCode( String code ) {
        return this.pinYinCodeSelectDao.getAllDepartmentByPinYinCode( code.toUpperCase().trim() );
    }

    public Map getPersonByPinYinCode( String code ) {
        return this.pinYinCodeSelectDao.getPersonByPinYinCode( code.toUpperCase().trim() );
    }

    public Map getChargeTypeByPinYinCode( String code ) {
        return this.pinYinCodeSelectDao.getChargeTypeByPinYinCode( code.toUpperCase().trim() );
    }

    public Map getChargeItemByPinYinCodeAndChargeType( String code, String chargeType ) {
        return this.pinYinCodeSelectDao.getChargeItemByPinYinCodeAndChargeType( code.toUpperCase().trim(), chargeType.trim() );
    }

    public Map getCostItemByPinYinCode( String code ) {
        return this.pinYinCodeSelectDao.getCostItemByPinYinCode( code.toUpperCase().trim() );
    }

}
