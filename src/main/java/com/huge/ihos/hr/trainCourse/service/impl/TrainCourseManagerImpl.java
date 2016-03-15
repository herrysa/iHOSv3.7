package com.huge.ihos.hr.trainCourse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainCourse.dao.TrainCourseDao;
import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.ihos.hr.trainCourse.service.TrainCourseManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainCourseManager")
public class TrainCourseManagerImpl extends GenericManagerImpl<TrainCourse, String> implements TrainCourseManager {
    private TrainCourseDao trainCourseDao;
    private BillNumberManager billNumberManager;

    @Autowired
    public TrainCourseManagerImpl(TrainCourseDao trainCourseDao) {
        super(trainCourseDao);
        this.trainCourseDao = trainCourseDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getTrainCourseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainCourseDao.getTrainCourseCriteria(paginatedList,filters);
	}
    @Override
    public TrainCourse saveTrainCourse(TrainCourse trainCourse,Boolean isEntityIsNew){
    	if(isEntityIsNew){
			trainCourse.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_TRAIN_COURSE,trainCourse.getYearMonth()));
		}
    	trainCourse=trainCourseDao.save(trainCourse);
    	return trainCourse;
    }
}