package com.huge.ihos.hr.hrOrg.dao;


import java.util.List;

import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrOrgSnap table.
 */
public interface HrOrgSnapDao extends GenericDao<HrOrgSnap, String> {
	public JQueryPager getHrOrgSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<String> getHisSnapIdList(String snapCode);

}