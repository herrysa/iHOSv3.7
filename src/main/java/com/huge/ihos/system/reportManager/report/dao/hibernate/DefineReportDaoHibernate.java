package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.dao.DefineReportDao;
import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;


@Repository("defineReportDao")
public class DefineReportDaoHibernate extends GenericDaoHibernate<DefineReport, String> implements DefineReportDao {

    public DefineReportDaoHibernate() {
        super(DefineReport.class);
    }
    
    public JQueryPager getDefineReportCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("code");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DefineReport.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDefineReportCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public void getFuncValue(ReportFunc reportFunc) {
		String rs;
		try {
			DataSource dataSource = SpringContextHelper.getDataSource();
			JdbcTemplate jtl = new JdbcTemplate(dataSource);
			rs = jtl.queryForObject(reportFunc.getFunc(), reportFunc.getPara(),String.class);
			reportFunc.setValue(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
