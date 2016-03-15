package com.huge.ihos.hr.trainInstitution.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainInstitution.dao.TrainInstitutionDao;
import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.ihos.hr.trainInstitution.service.TrainInstitutionManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainInstitutionManager")
public class TrainInstitutionManagerImpl extends GenericManagerImpl<TrainInstitution, String> implements TrainInstitutionManager {
    private TrainInstitutionDao trainInstitutionDao;

    @Autowired
    public TrainInstitutionManagerImpl(TrainInstitutionDao trainInstitutionDao) {
        super(trainInstitutionDao);
        this.trainInstitutionDao = trainInstitutionDao;
    }
    
    public JQueryPager getTrainInstitutionCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainInstitutionDao.getTrainInstitutionCriteria(paginatedList,filters);
	}
}