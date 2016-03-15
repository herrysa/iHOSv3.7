package com.huge.ihos.hr.trainCategory.dao;


import java.util.List;

import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainCategory table.
 */
public interface TrainCategoryDao extends GenericDao<TrainCategory, String> {
	public JQueryPager getTrainCategoryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}