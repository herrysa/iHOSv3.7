package com.huge.ihos.system.reportManager.report.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.ihos.system.reportManager.report.dao.UserReportDefineDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("userReportDefineDao")
public class UserReportDefineDaoHibernate extends GenericDaoHibernate<UserReportDefine, String> implements UserReportDefineDao {

    public UserReportDefineDaoHibernate() {
        super(UserReportDefine.class);
    }
    
    public JQueryPager getUserReportDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("defineId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, UserReportDefine.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getUserReportDefineCriteria", e);
			return paginatedList;
		}

	}
}
