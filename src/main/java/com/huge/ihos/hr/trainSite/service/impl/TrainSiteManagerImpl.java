package com.huge.ihos.hr.trainSite.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainSite.dao.TrainSiteDao;
import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.ihos.hr.trainSite.service.TrainSiteManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainSiteManager")
public class TrainSiteManagerImpl extends GenericManagerImpl<TrainSite, String> implements TrainSiteManager {
    private TrainSiteDao trainSiteDao;

    @Autowired
    public TrainSiteManagerImpl(TrainSiteDao trainSiteDao) {
        super(trainSiteDao);
        this.trainSiteDao = trainSiteDao;
    }
    
    public JQueryPager getTrainSiteCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainSiteDao.getTrainSiteCriteria(paginatedList,filters);
	}
}