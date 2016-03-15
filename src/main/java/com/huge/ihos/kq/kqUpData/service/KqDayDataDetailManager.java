package com.huge.ihos.kq.kqUpData.service;


import java.util.List;
import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqDayDataDetailManager extends GenericManager<KqDayDataDetail, String> {
     public JQueryPager getKqDayDataDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}