package com.huge.ihos.system.configuration.colsetting.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ColShowManager extends GenericManager<ColShow, String> {
     public JQueryPager getColShowCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public List<ColShow> getByEntityName(String entityName);
     
     public List<ColShow> getByTemplName(String templName,String entityName,String userId,String colshowType);
     
     public void delByTemplName(String templName,String entityName,String userId,String colshowType);
     
     public List<HashMap<String,String>> getAllTempl(String entityName,String userId,String colshowType);
     
     public List<HashMap<String,String>> getDeptTempl(String entityName,String userIds,String colshowType);
     
     public List<HashMap<String,String>> getRoleTempl(String entityName,String userIds);
     
     public List<HashMap<String,String>> getRoleTempl(String entityName,String userIds,String dept_userIds,String colshowType);
     
     public List<HashMap<String,String>> getPublicTempl(String entityName,String colshowType);
     
     public List<ColShow> getByFirstTempl(String entityName,String userId,String colshowType);
     /**
      * 根据部门查询
      * @param templName
      * @param entityName
      * @param userIds
      * @param colshowType
      * @return
      */
     public List<ColShow> getDeptTempletByName(String templName,String entityName,String[] userIds,String colshowType);
 	/**
 	 * 根据角色查询
 	 * @param templName
 	 * @param entityName
 	 * @param userIds
 	 * @param colshowType
 	 * @return
 	 */
 	 public List<ColShow> getRoleTempletByName(String templName,String entityName,String[] userIds,String colshowType);
 	/**
 	 * 公共模版
 	 * @param templName
 	 * @param entityName
 	 * @param userId
 	 * @param colshowType
 	 * @return
 	 */
 	 public List<ColShow> getPublicTempletByName(String templName,String entityName, String userId,String colshowType);
 	 /**
 	  * 取最新的模版
 	  * @param entityName
 	  * @param userId
 	  * @return
 	  */
 	 public ColShow getLastByTemplName(String entityName,String userId,String colshowType);
}