package com.huge.ihos.material.deptapp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.dao.DeptAppDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("deptAppDetailDao")
public class DeptAppDetailDaoHibernate extends GenericDaoHibernate<DeptAppDetail, String> implements DeptAppDetailDao {

    public DeptAppDetailDaoHibernate() {
        super(DeptAppDetail.class);
    }
    
    @SuppressWarnings("unchecked")
	public JQueryPager getDeptAppDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("deptAppDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptAppDetail.class, filters);
			paginatedList.setList((List<DeptAppDetail>) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptAppDetailCriteria", e);
			return paginatedList;
		}

	}
}
