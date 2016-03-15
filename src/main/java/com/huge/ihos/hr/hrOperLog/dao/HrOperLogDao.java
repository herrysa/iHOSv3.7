package com.huge.ihos.hr.hrOperLog.dao;


import java.util.List;

import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrOperLog table.
 */
public interface HrOperLogDao extends GenericDao<HrOperLog, String> {
	public JQueryPager getHrOperLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}