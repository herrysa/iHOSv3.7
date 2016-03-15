package com.huge.ihos.kaohe.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InspectTempl table.
 */
public interface InspectTemplDao extends GenericDao<InspectTempl, String> {
	public JQueryPager getInspectTemplCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public InspectTempl deptIsSelected(String deptId,String periodType,String selfTemplId);
}