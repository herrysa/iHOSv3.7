package com.huge.ihos.pz.businesstype.dao;


import java.util.List;

import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessTypeJ table.
 */
public interface BusinessTypeJDao extends GenericDao<BusinessTypeJ, String> {
	public JQueryPager getBusinessTypeJCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public BusinessTypeJ saveBusinessTypeJ(BusinessTypeJ businessTypeJ) throws Exception;
	
	public List<BusinessTypeJ> getAllByBusinessId(String businessId);
	
	public void deleteBusinessTypeJ(BusinessTypeJ businessTypeJ);
}