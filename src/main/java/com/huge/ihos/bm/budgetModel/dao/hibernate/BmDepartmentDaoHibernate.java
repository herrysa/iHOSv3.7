package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.model.BmDepartment;
import com.huge.ihos.bm.budgetModel.dao.BmDepartmentDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmDepartmentDao")
public class BmDepartmentDaoHibernate extends GenericDaoHibernate<BmDepartment, String> implements BmDepartmentDao {

    public BmDepartmentDaoHibernate() {
        super(BmDepartment.class);
    }
    
    public JQueryPager getBmDepartmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("departmentId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmDepartment.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmDepartmentCriteria", e);
			return paginatedList;
		}

	}
}
