package com.huge.ihos.hr.trainNeed.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.hr.trainNeed.dao.TrainNeedDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainNeedDao")
public class TrainNeedDaoHibernate extends GenericDaoHibernate<TrainNeed, String> implements TrainNeedDao {

    public TrainNeedDaoHibernate() {
        super(TrainNeed.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainNeedCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainNeed.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainNeedCriteria", e);
			return paginatedList;
		}

	}
}
