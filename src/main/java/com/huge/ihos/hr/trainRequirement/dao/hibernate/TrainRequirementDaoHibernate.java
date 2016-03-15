package com.huge.ihos.hr.trainRequirement.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirement;
import com.huge.ihos.hr.trainRequirement.dao.TrainRequirementDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainRequirementDao")
public class TrainRequirementDaoHibernate extends GenericDaoHibernate<TrainRequirement, String> implements TrainRequirementDao {

    public TrainRequirementDaoHibernate() {
        super(TrainRequirement.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainRequirementCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainRequirement.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainRequirementCriteria", e);
			return paginatedList;
		}

	}
}
