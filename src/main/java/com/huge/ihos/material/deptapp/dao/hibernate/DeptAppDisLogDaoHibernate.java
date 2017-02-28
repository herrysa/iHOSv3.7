package com.huge.ihos.material.deptapp.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.ihos.material.deptapp.dao.DeptAppDisLogDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptAppDisLogDao")
public class DeptAppDisLogDaoHibernate extends GenericDaoHibernate<DeptAppDisLog, String> implements DeptAppDisLogDao {

    public DeptAppDisLogDaoHibernate() {
        super(DeptAppDisLog.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getDeptAppDisLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptAppDisLog.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptAppDisLogCriteria", e);
			return paginatedList;
		}

	}
}
