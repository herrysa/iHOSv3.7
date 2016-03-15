package com.huge.ihos.hasp.service;

import org.sbbs.app.license.model.License;

import com.huge.foundation.common.AppException;


/*
 
  SELECT 
dbo.func_getUserCount('00') '数据交互平台',dbo.func_getUserCount('01') '成本核算系统',dbo.func_getUserCount('02') '决策支持系统',
dbo.func_getUserCount('03') '项目成本核算系统',dbo.func_getUserCount('04') '病种成本核算系统',dbo.func_getUserCount('05') '绩效考核系统',
dbo.func_getUserCount('06') '绩效分配系统',dbo.func_getUserCount('07') '物流管理系统',dbo.func_getUserCount('08') '固定资产管理系统',
dbo.func_getUserCount('09') '会计核算系统',dbo.func_getUserCount('10') '人力资源管理系统',dbo.func_getUserCount('11') '薪酬管理系统',
dbo.func_getUserCount('12') '预算管理系统',dbo.func_getUserCount('21') '科室奖金上报系统',dbo.func_getUserCount('50') '系统管理平台',dbo.func_getUserCount('60') '个人平台'
  
  
  


 */

public interface DogService {
    public int login() throws AppException;
    public int logout() throws AppException;
    public String getHospitalName()
        throws AppException;
    
    public String getOrgType()
    	throws AppException;
    
    public Integer getOrgNum()
    	throws AppException;

    public String getSoftWareVersion()
        throws AppException;

/*    public int getLicenseUserNumber()
        throws AppException;*/
    
    public String[] getAllowedSubSystem()
        throws AppException;
    
    public String getSoftWareCopyRight()   throws AppException;
    public int getSubSystemAllowUser(String subSysMenuId)   throws AppException;
    
    
    public String updateDogLicenseFromV2C(String v2cString)    throws AppException;
    
    public String getDogLincenseInfo()    throws AppException;
    
    public License getLicense()    throws AppException;
}
