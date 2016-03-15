package com.huge.ihos.hr.trainEquipment.dao;


import java.util.List;

import com.huge.ihos.hr.trainEquipment.model.TrainEquipment;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainEquipment table.
 */
public interface TrainEquipmentDao extends GenericDao<TrainEquipment, String> {
	public JQueryPager getTrainEquipmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}