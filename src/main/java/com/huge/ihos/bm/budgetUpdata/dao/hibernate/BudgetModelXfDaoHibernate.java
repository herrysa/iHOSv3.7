package com.huge.ihos.bm.budgetUpdata.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.dao.BudgetModelXfDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetModelXfDao")
public class BudgetModelXfDaoHibernate extends GenericDaoHibernate<BudgetModelXf, String> implements BudgetModelXfDao {

    public BudgetModelXfDaoHibernate() {
        super(BudgetModelXf.class);
    }
    
    public JQueryPager getBudgetModelXfCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("xfId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetModelXf.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetModelXfCriteria", e);
			return paginatedList;
		}

	}
}
