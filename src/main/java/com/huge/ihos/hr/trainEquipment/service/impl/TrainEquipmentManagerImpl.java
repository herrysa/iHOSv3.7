package com.huge.ihos.hr.trainEquipment.service.impl;

import java.util.List;
import com.huge.ihos.hr.trainEquipment.dao.TrainEquipmentDao;
import com.huge.ihos.hr.trainEquipment.model.TrainEquipment;
import com.huge.ihos.hr.trainEquipment.service.TrainEquipmentManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainEquipmentManager")
public class TrainEquipmentManagerImpl extends GenericManagerImpl<TrainEquipment, String> implements TrainEquipmentManager {
    private TrainEquipmentDao trainEquipmentDao;

    @Autowired
    public TrainEquipmentManagerImpl(TrainEquipmentDao trainEquipmentDao) {
        super(trainEquipmentDao);
        this.trainEquipmentDao = trainEquipmentDao;
    }
    
    public JQueryPager getTrainEquipmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainEquipmentDao.getTrainEquipmentCriteria(paginatedList,filters);
	}
}