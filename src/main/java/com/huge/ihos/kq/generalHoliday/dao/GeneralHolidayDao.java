package com.huge.ihos.kq.generalHoliday.dao;


import java.util.List;

import com.huge.ihos.kq.generalHoliday.model.GeneralHoliday;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GeneralHoliday table.
 */
public interface GeneralHolidayDao extends GenericDao<GeneralHoliday, String> {
	public JQueryPager getGeneralHolidayCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}