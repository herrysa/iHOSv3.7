package com.huge.ihos.kq.generalHoliday.dao;


import java.util.List;

import com.huge.ihos.kq.generalHoliday.model.GeneralHolidayChange;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GeneralHolidayChange table.
 */
public interface GeneralHolidayChangeDao extends GenericDao<GeneralHolidayChange, String> {
	public JQueryPager getGeneralHolidayChangeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}