package com.huge.ihos.kq.generalHoliday.service;


import java.util.List;
import com.huge.ihos.kq.generalHoliday.model.GeneralHoliday;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GeneralHolidayManager extends GenericManager<GeneralHoliday, String> {
     public JQueryPager getGeneralHolidayCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}