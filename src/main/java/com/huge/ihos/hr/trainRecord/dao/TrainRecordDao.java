package com.huge.ihos.hr.trainRecord.dao;


import java.util.List;

import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainRecord table.
 */
public interface TrainRecordDao extends GenericDao<TrainRecord, String> {
	public JQueryPager getTrainRecordCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}