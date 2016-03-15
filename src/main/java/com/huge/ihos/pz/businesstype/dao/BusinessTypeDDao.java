package com.huge.ihos.pz.businesstype.dao;


import java.util.List;

import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessTypeD table.
 */
public interface BusinessTypeDDao extends GenericDao<BusinessTypeD, String> {
	public JQueryPager getBusinessTypeDCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public BusinessTypeD saveBusinessTypeD(BusinessTypeD businessTypeD) throws Exception;
	
	public List<BusinessTypeD> getAllByBusinessId(String businessId);
	
	public void deleteBusinessTypeD(BusinessTypeD businessTypeD);
}