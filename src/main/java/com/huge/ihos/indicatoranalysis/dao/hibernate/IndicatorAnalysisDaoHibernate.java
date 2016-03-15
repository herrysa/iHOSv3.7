package com.huge.ihos.indicatoranalysis.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.indicatoranalysis.model.IndicatorAnalysis;
import com.huge.ihos.indicatoranalysis.dao.IndicatorAnalysisDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("indicatorAnalysisDao")
public class IndicatorAnalysisDaoHibernate extends GenericDaoHibernate<IndicatorAnalysis, String> implements IndicatorAnalysisDao {

    public IndicatorAnalysisDaoHibernate() {
        super(IndicatorAnalysis.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getIndicatorAnalysisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, IndicatorAnalysis.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getIndicatorAnalysisCriteria", e);
			return paginatedList;
		}
	}
}
