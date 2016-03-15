package com.huge.ihos.hr.trainTeacher.service;


import java.util.List;
import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainTeacherManager extends GenericManager<TrainTeacher, String> {
     public JQueryPager getTrainTeacherCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}