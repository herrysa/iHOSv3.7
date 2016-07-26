package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.model.BmModelDept;
import com.huge.ihos.bm.budgetModel.dao.BmModelDeptDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmModelDeptDao")
public class BmModelDeptDaoHibernate extends GenericDaoHibernate<BmModelDept, String> implements BmModelDeptDao {

    public BmModelDeptDaoHibernate() {
        super(BmModelDept.class);
    }
    
    public JQueryPager getBmModelDeptCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bmDeptId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmModelDept.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmModelDeptCriteria", e);
			return paginatedList;
		}

	}
}
