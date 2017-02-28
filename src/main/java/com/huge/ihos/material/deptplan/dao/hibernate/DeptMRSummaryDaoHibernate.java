package com.huge.ihos.material.deptplan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.dao.DeptMRSummaryDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptMRSummaryDao")
public class DeptMRSummaryDaoHibernate extends GenericDaoHibernate<DeptMRSummary, String> implements DeptMRSummaryDao {

    public DeptMRSummaryDaoHibernate() {
        super(DeptMRSummary.class);
    }
    
    public JQueryPager getDeptMRSummaryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("needId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptMRSummary.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptMRSummaryCriteria", e);
			return paginatedList;
		}

	}
}
