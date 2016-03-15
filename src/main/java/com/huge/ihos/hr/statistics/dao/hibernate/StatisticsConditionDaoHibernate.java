package com.huge.ihos.hr.statistics.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.ihos.hr.statistics.dao.StatisticsConditionDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("statisticsConditionDao")
public class StatisticsConditionDaoHibernate extends GenericDaoHibernate<StatisticsCondition, String> implements StatisticsConditionDao {

    public StatisticsConditionDaoHibernate() {
        super(StatisticsCondition.class);
    }
    
    public JQueryPager getStatisticsConditionCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, StatisticsCondition.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStatisticsConditionCriteria", e);
			return paginatedList;
		}

	}
}
