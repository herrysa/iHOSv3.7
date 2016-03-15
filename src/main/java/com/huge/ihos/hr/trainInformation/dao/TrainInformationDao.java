package com.huge.ihos.hr.trainInformation.dao;


import java.util.List;

import com.huge.ihos.hr.trainInformation.model.TrainInformation;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainInformation table.
 */
public interface TrainInformationDao extends GenericDao<TrainInformation, String> {
	public JQueryPager getTrainInformationCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}