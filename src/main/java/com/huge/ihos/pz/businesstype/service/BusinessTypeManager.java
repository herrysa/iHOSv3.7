package com.huge.ihos.pz.businesstype.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessTypeManager extends GenericManager<BusinessType, String> {
	public JQueryPager getBusinessTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 获取所有可用businessType(去除停用)
	 * @return
	 */
	public List<BusinessType> getAllAvailable();
	
	public BusinessType saveBusinessType(BusinessType businessType);
	
	public void delBusinessType(String businessId);
	
	public Map<String, Object> getAccountColMap(String businessId,String type);
	
	public List<Map<String, Object>> getAccountDataMaps(String businessId);
	
	public boolean checkBusinessTypeAdd(String businessId);
	
	public List<BusinessType> getAllDescendants(String businessId);
	
	public boolean checkDBTableExist(String tableName);
	
	public void createCollectTempTable(String businessId,String collectTempTable);
	
	public void checkBusinessType(String businessId);
	
	public JQueryPager getBusinessTypeQuery(final JQueryPager paginatedList,Map<String, String> sqlMap);
}