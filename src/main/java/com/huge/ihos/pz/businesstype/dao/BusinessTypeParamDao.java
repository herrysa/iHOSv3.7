package com.huge.ihos.pz.businesstype.dao;


import java.util.List;

import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessTypeParam table.
 */
public interface BusinessTypeParamDao extends GenericDao<BusinessTypeParam, String> {
	public JQueryPager getBusinessTypeParamCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}