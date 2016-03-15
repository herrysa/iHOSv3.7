package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.ihos.system.reportManager.report.dao.ReportPlanFilterDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("reportPlanFilterDao")
public class ReportPlanFilterDaoHibernate extends GenericDaoHibernate<ReportPlanFilter, String> implements ReportPlanFilterDao {

    public ReportPlanFilterDaoHibernate() {
        super(ReportPlanFilter.class);
    }
    
    public JQueryPager getReportPlanFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("filterId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ReportPlanFilter.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getReportPlanFilterCriteria", e);
			return paginatedList;
		}

	}
}
