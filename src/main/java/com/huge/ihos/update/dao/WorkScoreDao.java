package com.huge.ihos.update.dao;


import java.util.List;

import com.huge.ihos.update.model.WorkScore;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the WorkScore table.
 */
public interface WorkScoreDao extends GenericDao<WorkScore, String> {
	public JQueryPager getWorkScoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}