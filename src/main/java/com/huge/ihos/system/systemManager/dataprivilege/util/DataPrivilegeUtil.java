package com.huge.ihos.system.systemManager.dataprivilege.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.SpringContextHelper;

public class DataPrivilegeUtil {

	public static String depthTraversal(PrivilegeClass privilegeClass , String dataStr){
		BdInfo bdInfo = privilegeClass.getBdInfo();
		String fullDataStr = "";
		if(bdInfo.getTreeStructure()!=null&&bdInfo.getTreeStructure()){
			BdInfoUtil bdInfoUtil = new BdInfoUtil();
			bdInfoUtil.addBdInfo(bdInfo);
			String tableName = bdInfoUtil.bdInfo.getTableName();
			String parentCol = bdInfoUtil.getParentCol().getFieldCode();
			String pkCol = bdInfoUtil.getPkCol().getFieldCode();
			Map<String, Boolean> oldNodeMap = new HashMap<String, Boolean>();
			String[] dataArr = dataStr.split(",");
            for ( String data : dataArr ) {
            	oldNodeMap.put(data, true);
            }
			JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
    		String menurootSql = "select * from "+tableName+" where "+parentCol+" IS NULL";
    		List<Map<String, Object>> rootList = jt.queryForList(menurootSql);
            Map<String,Boolean> nodes = new HashMap<String, Boolean>();
            for(Map<String, Object> menu : rootList){
            	List<String> pathStack = new ArrayList<String>();
            	String menuId = menu.get(pkCol).toString();
            	traversalTree(bdInfoUtil,menuId ,pathStack,nodes ,false ,oldNodeMap);
            }
            Set<String> keySet = nodes.keySet();
            for(String key : keySet){
            	fullDataStr += key+",";
            }
            if(!"".equals(fullDataStr)){
            	fullDataStr = OtherUtil.subStrEnd(fullDataStr, ",");
            }
		}
		if("".equals(fullDataStr)){
			fullDataStr = dataStr;
		}
		return fullDataStr;
	}
	
	private static void traversalTree(BdInfoUtil bdInfoUtil,String parent,List<String> pathStack , Map<String,Boolean> nodes , boolean isShow,Map<String, Boolean> oldNodeMap){
		String tableName = bdInfoUtil.bdInfo.getTableName();
		String parentCol = bdInfoUtil.getParentCol().getFieldCode();
		String pkCol = bdInfoUtil.getPkCol().getFieldCode();
		pathStack.add(parent);
    	boolean showCharge = isShow==true?true:oldNodeMap.get(parent)==null?false:true;
    	JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String childrenSql = "select * from "+tableName+" where "+parentCol+"='"+parent+"'";
		List<Map<String, Object>> childrenList = jt.queryForList(childrenSql);
    	if(showCharge&&(childrenList==null||childrenList.size()==0)){
    		for(String path : pathStack){
    			nodes.put(path,true);
    		}
    		pathStack.remove(parent);
    	}
    	for(Map<String, Object> child : childrenList){
    		String childId = child.get(pkCol).toString();
    		traversalTree(bdInfoUtil,childId ,pathStack,nodes ,showCharge ,oldNodeMap);
    	}
    }
	
	public static String dealAllPrivilege(PrivilegeClass privilegeClass) {
		BdInfo bdInfo = privilegeClass.getBdInfo();
		BdInfoUtil bdInfoUtil = new BdInfoUtil();
		bdInfoUtil.addBdInfo(bdInfo);
		return bdInfoUtil.makeSubQuerySQL();
	}
	
	public static String formatDataPrivilege(PrivilegeClass privilegeClass, String dataStr,String user){
		String userId = null;
		if(user!=null){
			userId = user;
		}else{
			userId = UserContextUtil.getLoginUserId();
		}
		if(dataStr.contains("all")||dataStr.contains("ALL")){
			return dealAllPrivilege(privilegeClass);
		}else{
			return dataStr;
		}
		/*else{
			return depthTraversal(privilegeClass,dataStr);
		}*/
	}
}
