package com.huge.ihos.hr.trainInformation.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainInformation.dao.TrainInformationDao;
import com.huge.ihos.hr.trainInformation.model.TrainInformation;
import com.huge.ihos.hr.trainInformation.service.TrainInformationManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainInformationManager")
public class TrainInformationManagerImpl extends GenericManagerImpl<TrainInformation, String> implements TrainInformationManager {
    private TrainInformationDao trainInformationDao;

    @Autowired
    public TrainInformationManagerImpl(TrainInformationDao trainInformationDao) {
        super(trainInformationDao);
        this.trainInformationDao = trainInformationDao;
    }
    
    public JQueryPager getTrainInformationCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainInformationDao.getTrainInformationCriteria(paginatedList,filters);
	}
}