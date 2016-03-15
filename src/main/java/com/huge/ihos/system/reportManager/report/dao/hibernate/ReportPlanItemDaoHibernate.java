package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.ihos.system.reportManager.report.dao.ReportPlanItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("reportPlanItemDao")
public class ReportPlanItemDaoHibernate extends GenericDaoHibernate<ReportPlanItem, String> implements ReportPlanItemDao {

    public ReportPlanItemDaoHibernate() {
        super(ReportPlanItem.class);
    }
    
    public JQueryPager getReportPlanItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("colId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ReportPlanItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getReportPlanItemCriteria", e);
			return paginatedList;
		}

	}
}
