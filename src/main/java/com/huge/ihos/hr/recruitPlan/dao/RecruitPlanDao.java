package com.huge.ihos.hr.recruitPlan.dao;


import java.util.List;

import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the RecruitPlan table.
 */
public interface RecruitPlanDao extends GenericDao<RecruitPlan, String> {
	public JQueryPager getRecruitPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}