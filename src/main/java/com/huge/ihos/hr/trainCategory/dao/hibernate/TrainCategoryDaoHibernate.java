package com.huge.ihos.hr.trainCategory.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.ihos.hr.trainCategory.dao.TrainCategoryDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainCategoryDao")
public class TrainCategoryDaoHibernate extends GenericDaoHibernate<TrainCategory, String> implements TrainCategoryDao {

    public TrainCategoryDaoHibernate() {
        super(TrainCategory.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainCategoryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainCategory.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainCategoryCriteria", e);
			return paginatedList;
		}

	}
}
