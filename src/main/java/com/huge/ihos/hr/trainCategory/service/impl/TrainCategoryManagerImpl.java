package com.huge.ihos.hr.trainCategory.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainCategory.dao.TrainCategoryDao;
import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.ihos.hr.trainCategory.service.TrainCategoryManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainCategoryManager")
public class TrainCategoryManagerImpl extends GenericManagerImpl<TrainCategory, String> implements TrainCategoryManager {
    private TrainCategoryDao trainCategoryDao;

    @Autowired
    public TrainCategoryManagerImpl(TrainCategoryDao trainCategoryDao) {
        super(trainCategoryDao);
        this.trainCategoryDao = trainCategoryDao;
    }
    
    public JQueryPager getTrainCategoryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainCategoryDao.getTrainCategoryCriteria(paginatedList,filters);
	}
}