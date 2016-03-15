package com.huge.ihos.hr.trainInstitution.dao;


import java.util.List;

import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainInstitution table.
 */
public interface TrainInstitutionDao extends GenericDao<TrainInstitution, String> {
	public JQueryPager getTrainInstitutionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}