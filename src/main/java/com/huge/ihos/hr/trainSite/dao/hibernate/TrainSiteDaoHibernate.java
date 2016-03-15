package com.huge.ihos.hr.trainSite.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.ihos.hr.trainSite.dao.TrainSiteDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainSiteDao")
public class TrainSiteDaoHibernate extends GenericDaoHibernate<TrainSite, String> implements TrainSiteDao {

    public TrainSiteDaoHibernate() {
        super(TrainSite.class);
    }
    
    public JQueryPager getTrainSiteCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainSite.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainSiteCriteria", e);
			return paginatedList;
		}

	}
}
