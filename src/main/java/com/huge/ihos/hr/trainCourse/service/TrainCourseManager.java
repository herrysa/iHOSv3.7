package com.huge.ihos.hr.trainCourse.service;


import java.util.List;
import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainCourseManager extends GenericManager<TrainCourse, String> {
     public JQueryPager getTrainCourseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public TrainCourse saveTrainCourse(TrainCourse trainCourse,Boolean isEntityIsNew);   
}