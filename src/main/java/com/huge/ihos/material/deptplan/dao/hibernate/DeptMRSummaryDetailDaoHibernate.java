package com.huge.ihos.material.deptplan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.dao.DeptMRSummaryDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptMRSummaryDetailDao")
public class DeptMRSummaryDetailDaoHibernate extends GenericDaoHibernate<DeptMRSummaryDetail, String> implements DeptMRSummaryDetailDao {

    public DeptMRSummaryDetailDaoHibernate() {
        super(DeptMRSummaryDetail.class);
    }
    
    public JQueryPager getDeptMRSummaryDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("mrDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptMRSummaryDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptMRSummaryDetailCriteria", e);
			return paginatedList;
		}

	}
}
