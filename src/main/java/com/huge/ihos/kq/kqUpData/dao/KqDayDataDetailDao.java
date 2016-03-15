package com.huge.ihos.kq.kqUpData.dao;


import java.util.List;

import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqDayDataDetail table.
 */
public interface KqDayDataDetailDao extends GenericDao<KqDayDataDetail, String> {
	public JQueryPager getKqDayDataDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}