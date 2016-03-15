package com.huge.ihos.pz.businesstype.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessType table.
 */
public interface BusinessTypeDao extends GenericDao<BusinessType, String> {
	public JQueryPager getBusinessTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<BusinessType> getAllAvailable();
	
	public BusinessType saveBusinessType(BusinessType businessType);
	
	public List<BusinessType> getBusinessTypeByIds(String ids);
	
	public Map<String, Object> getSysTableByName(String tableName);
	
	public void delBusinessType(String businessId);
	
	public List<BusinessType> getAllDescendants(String businessId);
	
	public JQueryPager getBusinessTypeQuery(final JQueryPager paginatedList,Map<String, String> sqlMap);
}