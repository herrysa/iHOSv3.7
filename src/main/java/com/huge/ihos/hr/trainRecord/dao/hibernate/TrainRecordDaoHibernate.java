package com.huge.ihos.hr.trainRecord.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.hr.trainRecord.dao.TrainRecordDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainRecordDao")
public class TrainRecordDaoHibernate extends GenericDaoHibernate<TrainRecord, String> implements TrainRecordDao {

    public TrainRecordDaoHibernate() {
        super(TrainRecord.class);
    }
    
    public JQueryPager getTrainRecordCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainRecord.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainRecordCriteria", e);
			return paginatedList;
		}

	}
}
