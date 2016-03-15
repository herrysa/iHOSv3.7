package com.huge.ihos.hr.trainEquipment.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainEquipment.model.TrainEquipment;
import com.huge.ihos.hr.trainEquipment.dao.TrainEquipmentDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainEquipmentDao")
public class TrainEquipmentDaoHibernate extends GenericDaoHibernate<TrainEquipment, String> implements TrainEquipmentDao {

    public TrainEquipmentDaoHibernate() {
        super(TrainEquipment.class);
    }
    
    public JQueryPager getTrainEquipmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainEquipment.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainEquipmentCriteria", e);
			return paginatedList;
		}

	}
}
