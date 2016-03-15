package com.huge.ihos.hr.hrOrg.dao;


import java.util.List;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrOrg table.
 */
public interface HrOrgDao extends GenericDao<HrOrg, String> {
	public JQueryPager getHrOrgCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}