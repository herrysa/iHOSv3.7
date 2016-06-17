package com.huge.ihos.hasp.service.impl;

import org.sbbs.app.license.model.License;

import com.huge.foundation.common.AppException;
import com.huge.ihos.hasp.service.DogService;

public class DummyDogManager
    implements DogService {

    @Override
    public String getHospitalName()
        throws AppException {
        // TODO Auto-generated method stub
        return "Dummy";
    }

    @Override
    public String getSoftWareVersion()
        throws AppException {
        // TODO Auto-generated method stub
        return "V3.0";
    }

    /*
     * @Override public int getLicenseUserNumber() throws AppException {  return -1; }
     */

    @Override
    public String[] getAllowedSubSystem()
        throws AppException {
        String[] dummySubSys = { "00", "01", "0201","0202", "03", "04", "05", "0601","0602","0603","0604", "40" ,"50", "60","90","10","09","11","12" };
        return dummySubSys;
    }

    @Override
    public String getSoftWareCopyRight()
        throws AppException {
        // TODO Auto-generated method stub
        return "瑞智龙腾版权所有";
    }

    @Override
    public int getSubSystemAllowUser( String subSysMenuId )
        throws AppException {
        // TODO Auto-generated method stub
        if ( subSysMenuId.equals( "00" ) )
            return -1;
        else if ( subSysMenuId.equals( "01" ) )
        	return -1;
        else if ( subSysMenuId.equals( "02" ) )
        	return -1;
        else if ( subSysMenuId.equals( "03" ) )
        	return -1;
        else if ( subSysMenuId.equals( "04" ) )
        	return -1;
        else if ( subSysMenuId.equals( "05" ) )
        	return -1;
        else if ( subSysMenuId.equals( "06" ) )
        	return -1; 
        else if ( subSysMenuId.equals( "07" ) )
        	return -1;
        else if ( subSysMenuId.equals( "08" ) )
        	return -1;
        else if ( subSysMenuId.equals( "09" ) )
        	return -1;
        else if ( subSysMenuId.equals( "50" ) )
        	return -1;
        else if ( subSysMenuId.equals( "60" ) )
        	return -1;
        else if ( subSysMenuId.equals( "11" ) )
        	return -1;
        else
        	return -1;
    }

    @Override
    public String updateDogLicenseFromV2C( String v2cString )
        throws AppException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDogLincenseInfo()
        throws AppException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public License getLicense()
        throws AppException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int login()
        throws AppException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int logout()
        throws AppException {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public String getOrgType() throws AppException {
		// TODO Auto-generated method stub
		return "S1";
	}

	@Override
	public Integer getOrgNum() throws AppException {
		// TODO Auto-generated method stub
		return 1;
	}

}
