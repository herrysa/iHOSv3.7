package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.dao.BudgetModelDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetModelDao")
public class BudgetModelDaoHibernate extends GenericDaoHibernate<BudgetModel, String> implements BudgetModelDao {

    public BudgetModelDaoHibernate() {
        super(BudgetModel.class);
    }
    
    public JQueryPager getBudgetModelCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("modelId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetModel.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetModelCriteria", e);
			return paginatedList;
		}

	}
}
