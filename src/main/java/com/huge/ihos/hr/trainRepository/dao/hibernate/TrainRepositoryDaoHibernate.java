package com.huge.ihos.hr.trainRepository.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainRepository.model.TrainRepository;
import com.huge.ihos.hr.trainRepository.dao.TrainRepositoryDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainRepositoryDao")
public class TrainRepositoryDaoHibernate extends GenericDaoHibernate<TrainRepository, String> implements TrainRepositoryDao {

    public TrainRepositoryDaoHibernate() {
        super(TrainRepository.class);
    }
    
    public JQueryPager getTrainRepositoryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainRepository.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainRepositoryCriteria", e);
			return paginatedList;
		}

	}
}
