package com.huge.ihos.hr.trainRequirement.dao;


import java.util.List;

import com.huge.ihos.hr.trainRequirement.model.TrainRequirement;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainRequirement table.
 */
public interface TrainRequirementDao extends GenericDao<TrainRequirement, String> {
	public JQueryPager getTrainRequirementCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}