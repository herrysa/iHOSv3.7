package com.huge.ihos.bm.index.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.dao.BudgetIndexDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("budgetIndexDao")
public class BudgetIndexDaoHibernate extends GenericDaoHibernate<BudgetIndex, String> implements BudgetIndexDao {

    public BudgetIndexDaoHibernate() {
        super(BudgetIndex.class);
    }
    
    public JQueryPager getBudgetIndexCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("indexCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BudgetIndex.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBudgetIndexCriteria", e);
			return paginatedList;
		}

	}
}
