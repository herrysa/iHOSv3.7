package com.huge.webapp.taglib;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONObject;

import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.util.javabean.MapUtils;

public class FnsTag {
	
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

	public static String getGlobalParam(String paramKey,String subSystemCode){
		return ContextUtil.getGlobalParamByKey(paramKey, subSystemCode);
	}
	
	public static String getGlobalParam(String paramKey){
		return ContextUtil.getGlobalParamByKey(paramKey);
	}
	
	public static boolean hasRole(String role){
		Set<Role> roles = UserContextUtil.getContextRoles();
		Iterator<Role> roleIt = roles.iterator();
		while(roleIt.hasNext()){
			Role roleTemp = roleIt.next();
			String roleName = roleTemp.getName();
			if(role.equals(roleName)){
				return true;
			}
		}
		return false;
	}
	
	public static String userContextParam(String paramKey){
		String value = "";
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			Map<String, String> userContextMap = MapUtils.toMap(userContext);
			value = userContextMap.get(paramKey);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static Set getSubSystems(){
		Set<Map<String, Object>> subSystemSet = new TreeSet<Map<String, Object>>();
		try {
			subSystemSet = ContextUtil.getAllowSubSystem("0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subSystemSet;
	}
	
	public static Set getUserSubSystems(){
		Set<Menu> subSystemSet = new TreeSet<Menu>();
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			Set<Menu> sysRootMenus = userContext.getUserSysRootMenus();
			Set<Menu> busRootMenus = userContext.getUserBusRootMenus();
			subSystemSet.addAll(sysRootMenus);
			subSystemSet.addAll(busRootMenus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subSystemSet;
	}
	
	public static String getVariable(String name){
		UserContext userContext = UserContextUtil.getUserContext();
		if(userContext!=null){
			Map sysVarMap = userContext.getSysVars();
			Object value = sysVarMap.get(name);
			if(value!=null){
				return value.toString();
			}else {
				return "";
			}
		}else{
			return "";
		}
	}
	
	public static String getHerpType(){
		return ContextUtil.herpType;
	}
	
	public static String getHerpParam(String key,String subSystemCode){
		/*String loginPeriod = UserContextUtil.getLoginPeriod();
		if(loginPeriod==null||"".equals(loginPeriod)){
			return "";
		}
		String loginYear = loginPeriod.substring(0, 4);*/
		String paramValue = ContextUtil.getHerpParamByKey(key, subSystemCode);
		return paramValue;
	}
	
	public static String getAllVariableStr(){
		UserContext userContext = UserContextUtil.getUserContext();
		if(userContext!=null){
			Map sysVarMap = userContext.getSysVars();
			JSONObject sysVarJson = JSONObject.fromObject(sysVarMap);
			String varStr =  sysVarJson.toString();
			return varStr;
		}else{
			return "";
		}
	}
}
