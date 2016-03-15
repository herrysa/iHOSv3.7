package com.huge.ihos.hr.trainEquipment.service;


import java.util.List;
import com.huge.ihos.hr.trainEquipment.model.TrainEquipment;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainEquipmentManager extends GenericManager<TrainEquipment, String> {
     public JQueryPager getTrainEquipmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}