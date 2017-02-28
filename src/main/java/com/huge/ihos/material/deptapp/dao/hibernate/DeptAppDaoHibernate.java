package com.huge.ihos.material.deptapp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.dao.DeptAppDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("deptAppDao")
public class DeptAppDaoHibernate extends GenericDaoHibernate<DeptApp, String> implements DeptAppDao {

    public DeptAppDaoHibernate() {
        super(DeptApp.class);
    }
    
    @SuppressWarnings("unchecked")
	public JQueryPager getDeptAppCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("deptAppId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptApp.class, filters);
			paginatedList.setList((List<DeptApp>) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptAppCriteria", e);
			return paginatedList;
		}

	}
}
