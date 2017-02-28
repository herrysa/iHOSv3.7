package com.huge.ihos.material.deptapp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.ihos.material.deptapp.dao.DeptAppDLDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptAppDLDetailDao")
public class DeptAppDLDetailDaoHibernate extends GenericDaoHibernate<DeptAppDLDetail, String> implements DeptAppDLDetailDao {

    public DeptAppDLDetailDaoHibernate() {
        super(DeptAppDLDetail.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getDeptAppDLDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptAppDLDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptAppDLDetailCriteria", e);
			return paginatedList;
		}

	}
}
