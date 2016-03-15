package com.huge.webapp.taglib;

import com.huge.ihos.system.context.UserContextUtil;

/**
 * @author zzh
 * EL表达式函数处理类
 */
public class ElTag {

	public static String u_writeDP(String dpClassCode){  
        return UserContextUtil.findUserDataPrivilegeStr(dpClassCode, "2"); 
    }
	
	public static String u_readDP(String dpClassCode){  
        return UserContextUtil.findUserDataPrivilegeStr(dpClassCode, "1"); 
    }
	
	public static String u_writeDPSql(String dpClassCode){  
        return UserContextUtil.findUserDataPrivilegeSql(dpClassCode, "2"); 
    }
	
	public static String u_readDPSql(String dpClassCode){  
        return UserContextUtil.findUserDataPrivilegeSql(dpClassCode, "1"); 
    }
	
	public static String userDPSql(String userId , String dpClassCode){
		String priviStr = UserContextUtil.getUserManager().getDataPrivatesByClass2Sql(userId,dpClassCode);
        return priviStr; 
    }
}
