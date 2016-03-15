package com.huge.ihos.hr.trainRecord.dao;


import java.util.List;

import com.huge.ihos.hr.trainRecord.model.TrainRecordDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainRecordDetail table.
 */
public interface TrainRecordDetailDao extends GenericDao<TrainRecordDetail, String> {
	public JQueryPager getTrainRecordDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void saveTrainRecordDetailGridDate(String gridAllDatas,String recordId);
}