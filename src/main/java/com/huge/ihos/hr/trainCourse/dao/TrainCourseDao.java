package com.huge.ihos.hr.trainCourse.dao;


import java.util.List;

import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainCourse table.
 */
public interface TrainCourseDao extends GenericDao<TrainCourse, String> {
	public JQueryPager getTrainCourseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}