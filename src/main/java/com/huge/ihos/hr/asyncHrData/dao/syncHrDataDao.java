package com.huge.ihos.hr.asyncHrData.dao;


import java.util.List;

import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the syncHrData table.
 */
public interface syncHrDataDao extends GenericDao<syncHrData, String> {
	public JQueryPager getsyncHrDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}