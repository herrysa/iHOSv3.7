package com.huge.ihos.hr.hrPerson.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonPK;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrPersonHis table.
 */
public interface HrPersonHisDao extends GenericDao<HrPersonHis, HrPersonPK> {
	public JQueryPager getHrPersonHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}