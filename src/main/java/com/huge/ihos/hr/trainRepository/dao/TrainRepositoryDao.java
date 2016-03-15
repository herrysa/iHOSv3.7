package com.huge.ihos.hr.trainRepository.dao;


import java.util.List;

import com.huge.ihos.hr.trainRepository.model.TrainRepository;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainRepository table.
 */
public interface TrainRepositoryDao extends GenericDao<TrainRepository, String> {
	public JQueryPager getTrainRepositoryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}