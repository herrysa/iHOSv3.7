package com.huge.ihos.hr.recruitNeed.dao;


import java.util.List;

import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the RecruitNeed table.
 */
public interface RecruitNeedDao extends GenericDao<RecruitNeed, String> {
	public JQueryPager getRecruitNeedCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}