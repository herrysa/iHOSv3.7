package com.huge.ihos.hr.trainTeacher.dao;


import java.util.List;

import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainTeacher table.
 */
public interface TrainTeacherDao extends GenericDao<TrainTeacher, String> {
	public JQueryPager getTrainTeacherCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}