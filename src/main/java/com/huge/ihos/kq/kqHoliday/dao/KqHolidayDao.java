package com.huge.ihos.kq.kqHoliday.dao;


import java.util.List;

import com.huge.ihos.kq.kqHoliday.model.KqHoliday;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqHoliday table.
 */
public interface KqHolidayDao extends GenericDao<KqHoliday, String> {
	public JQueryPager getKqHolidayCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}