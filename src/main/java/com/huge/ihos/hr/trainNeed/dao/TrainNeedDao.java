package com.huge.ihos.hr.trainNeed.dao;


import java.util.List;

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainNeed table.
 */
public interface TrainNeedDao extends GenericDao<TrainNeed, String> {
	public JQueryPager getTrainNeedCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}