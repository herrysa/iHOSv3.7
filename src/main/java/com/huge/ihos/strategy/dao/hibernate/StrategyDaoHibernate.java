package com.huge.ihos.strategy.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.strategy.model.Strategy;
import com.huge.ihos.strategy.dao.StrategyDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("strategyDao")
public class StrategyDaoHibernate extends GenericDaoHibernate<Strategy, Integer> implements StrategyDao {

    public StrategyDaoHibernate() {
        super(Strategy.class);
    }
    
    public JQueryPager getStrategyCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("strategyId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Strategy.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStrategyCriteria", e);
			return paginatedList;
		}

	}
}
