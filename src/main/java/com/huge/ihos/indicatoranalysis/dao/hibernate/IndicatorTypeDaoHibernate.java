package com.huge.ihos.indicatoranalysis.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.ihos.indicatoranalysis.dao.IndicatorTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("indicatorTypeDao")
public class IndicatorTypeDaoHibernate extends GenericDaoHibernate<IndicatorType, String> implements IndicatorTypeDao {

    public IndicatorTypeDaoHibernate() {
        super(IndicatorType.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getIndicatorTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, IndicatorType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getIndicatorTypeCriteria", e);
			return paginatedList;
		}

	}
}
