package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.model.ReportDataSource;
import com.huge.ihos.system.reportManager.report.dao.ReportDataSourceDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("reportDataSourceDao")
public class ReportDataSourceDaoHibernate extends GenericDaoHibernate<ReportDataSource, String> implements ReportDataSourceDao {

    public ReportDataSourceDaoHibernate() {
        super(ReportDataSource.class);
    }
    
    public JQueryPager getReportDataSourceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("code");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ReportDataSource.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getReportDataSourceCriteria", e);
			return paginatedList;
		}

	}
}
