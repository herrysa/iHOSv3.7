package com.huge.ihos.hr.trainTeacher.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainTeacher.dao.TrainTeacherDao;
import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.ihos.hr.trainTeacher.service.TrainTeacherManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainTeacherManager")
public class TrainTeacherManagerImpl extends GenericManagerImpl<TrainTeacher, String> implements TrainTeacherManager {
    private TrainTeacherDao trainTeacherDao;

    @Autowired
    public TrainTeacherManagerImpl(TrainTeacherDao trainTeacherDao) {
        super(trainTeacherDao);
        this.trainTeacherDao = trainTeacherDao;
    }
    
    public JQueryPager getTrainTeacherCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainTeacherDao.getTrainTeacherCriteria(paginatedList,filters);
	}
}