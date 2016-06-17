package com.huge.ihos.bm.budgetType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.ihos.bm.budgetType.dao.BudgetTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetTypeDao")
public class BudgetTypeDaoHibernate extends GenericDaoHibernate<BudgetType, String> implements BudgetTypeDao {

    public BudgetTypeDaoHibernate() {
        super(BudgetType.class);
    }
    
    public JQueryPager getBudgetTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("budgetTypeCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetTypeCriteria", e);
			return paginatedList;
		}

	}
}
