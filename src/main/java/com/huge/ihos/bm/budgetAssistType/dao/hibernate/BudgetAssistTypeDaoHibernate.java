package com.huge.ihos.bm.budgetAssistType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.ihos.bm.budgetAssistType.dao.BudgetAssistTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetAssistTypeDao")
public class BudgetAssistTypeDaoHibernate extends GenericDaoHibernate<BudgetAssistType, String> implements BudgetAssistTypeDao {

    public BudgetAssistTypeDaoHibernate() {
        super(BudgetAssistType.class);
    }
    
    public JQueryPager getBudgetAssistTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("colCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetAssistType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetAssistTypeCriteria", e);
			return paginatedList;
		}

	}
}
