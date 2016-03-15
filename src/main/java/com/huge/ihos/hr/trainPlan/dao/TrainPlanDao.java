package com.huge.ihos.hr.trainPlan.dao;


import java.util.List;

import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainPlan table.
 */
public interface TrainPlanDao extends GenericDao<TrainPlan, String> {
	public JQueryPager getTrainPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}