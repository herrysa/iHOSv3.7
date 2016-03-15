package com.huge.ihos.hr.trainInformation.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainInformation.model.TrainInformation;
import com.huge.ihos.hr.trainInformation.dao.TrainInformationDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainInformationDao")
public class TrainInformationDaoHibernate extends GenericDaoHibernate<TrainInformation, String> implements TrainInformationDao {

    public TrainInformationDaoHibernate() {
        super(TrainInformation.class);
    }
    
    public JQueryPager getTrainInformationCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainInformation.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainInformationCriteria", e);
			return paginatedList;
		}

	}
}
