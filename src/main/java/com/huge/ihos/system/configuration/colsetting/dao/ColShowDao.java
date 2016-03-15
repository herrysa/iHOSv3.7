package com.huge.ihos.system.configuration.colsetting.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ColShow table.
 */
public interface ColShowDao extends GenericDao<ColShow, String> {
	public JQueryPager getColShowCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ColShow> getByEntityName(String entityName);
	
	public List<ColShow> getByTemplName(String templName,String entityName,String userId,String colshowType);
	
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId,String colshowType);
	
	public List<HashMap<String,String>> getDeptTempl(String entityName,String userIds,String colshowType);
	
	public List<HashMap<String,String>> getRoleTempl(String entityName,String userIds);
	
	public List<HashMap<String,String>> getRoleTempl(String entityName,String userIds,String dept_userIds,String colshowType);
	
	public List<HashMap<String,String>> getPublicTempl(String entityName,String colshowType);
	
	public List<ColShow> getDeptTempletByName(String templName,String entityName,String[] userIds,String colshowType);
	
	public List<ColShow> getRoleTempletByName(String templName,String entityName,String[] userIds,String colshowType);
	
	public List<ColShow> getPublicTempletByName(String templName,String entityName, String userId,String colshowType);
}