package com.huge.ihos.hr.trainPlan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainPlan.dao.TrainPlanDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainPlanDao")
public class TrainPlanDaoHibernate extends GenericDaoHibernate<TrainPlan, String> implements TrainPlanDao {

    public TrainPlanDaoHibernate() {
        super(TrainPlan.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainPlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainPlanCriteria", e);
			return paginatedList;
		}

	}
}
