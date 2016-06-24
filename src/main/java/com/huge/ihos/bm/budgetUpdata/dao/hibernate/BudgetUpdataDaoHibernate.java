package com.huge.ihos.bm.budgetUpdata.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.dao.BudgetUpdataDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetUpdataDao")
public class BudgetUpdataDaoHibernate extends GenericDaoHibernate<BudgetUpdata, String> implements BudgetUpdataDao {

    public BudgetUpdataDaoHibernate() {
        super(BudgetUpdata.class);
    }
    
    public JQueryPager getBudgetUpdataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("updataId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetUpdata.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetUpdataCriteria", e);
			return paginatedList;
		}

	}
}
