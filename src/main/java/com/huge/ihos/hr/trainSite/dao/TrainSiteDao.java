package com.huge.ihos.hr.trainSite.dao;


import java.util.List;

import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainSite table.
 */
public interface TrainSiteDao extends GenericDao<TrainSite, String> {
	public JQueryPager getTrainSiteCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}