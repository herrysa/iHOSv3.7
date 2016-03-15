package com.huge.ihos.nursescore.dao;


import java.util.Date;
import java.util.List;

import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the NurseDayScore table.
 */
public interface NurseDayScoreDao extends GenericDao<NurseDayScore, Long> {
	public JQueryPager getNurseDayScoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<NurseDayScore> findByScoreDate(String deptId,Date date);
}