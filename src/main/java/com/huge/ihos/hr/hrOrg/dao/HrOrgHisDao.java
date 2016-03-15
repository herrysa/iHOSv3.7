package com.huge.ihos.hr.hrOrg.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgPk;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrOrgHis table.
 */
public interface HrOrgHisDao extends GenericDao<HrOrgHis, HrOrgPk> {
	public JQueryPager getHrOrgHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}