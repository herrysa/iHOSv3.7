package com.huge.ihos.hr.trainRepository.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainRepository.dao.TrainRepositoryDao;
import com.huge.ihos.hr.trainRepository.model.TrainRepository;
import com.huge.ihos.hr.trainRepository.service.TrainRepositoryManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainRepositoryManager")
public class TrainRepositoryManagerImpl extends GenericManagerImpl<TrainRepository, String> implements TrainRepositoryManager {
    private TrainRepositoryDao trainRepositoryDao;

    @Autowired
    public TrainRepositoryManagerImpl(TrainRepositoryDao trainRepositoryDao) {
        super(trainRepositoryDao);
        this.trainRepositoryDao = trainRepositoryDao;
    }
    
    public JQueryPager getTrainRepositoryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainRepositoryDao.getTrainRepositoryCriteria(paginatedList,filters);
	}
}