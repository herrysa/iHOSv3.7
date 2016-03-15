package com.huge.ihos.hr.statistics.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.ihos.hr.statistics.dao.StatisticsTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("statisticsTypeDao")
public class StatisticsTypeDaoHibernate extends GenericDaoHibernate<StatisticsType, String> implements StatisticsTypeDao {

    public StatisticsTypeDaoHibernate() {
        super(StatisticsType.class);
    }
    
    public JQueryPager getStatisticsTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, StatisticsType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStatisticsTypeCriteria", e);
			return paginatedList;
		}

	}
}
