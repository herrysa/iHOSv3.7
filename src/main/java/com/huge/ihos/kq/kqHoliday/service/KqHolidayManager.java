package com.huge.ihos.kq.kqHoliday.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqHoliday.model.KqHoliday;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqHolidayManager extends GenericManager<KqHoliday, String> {
     public JQueryPager getKqHolidayCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 某一月哪一天是公休
      * @param year
      * @param month
      * @param days
      * @return
      */
     public Map<String, Boolean> getMonthHoliday(int year,int month,int days);
}