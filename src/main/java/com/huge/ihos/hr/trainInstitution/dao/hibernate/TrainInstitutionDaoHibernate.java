package com.huge.ihos.hr.trainInstitution.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.ihos.hr.trainInstitution.dao.TrainInstitutionDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainInstitutionDao")
public class TrainInstitutionDaoHibernate extends GenericDaoHibernate<TrainInstitution, String> implements TrainInstitutionDao {

    public TrainInstitutionDaoHibernate() {
        super(TrainInstitution.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainInstitutionCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainInstitution.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainInstitutionCriteria", e);
			return paginatedList;
		}

	}
}
