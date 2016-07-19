package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.dao.BmModelProcessDao;
import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmModelProcessDao")
public class BmModelProcessDaoHibernate extends GenericDaoHibernate<BmModelProcess, String> implements BmModelProcessDao {

    public BmModelProcessDaoHibernate() {
        super(BmModelProcess.class);
    }
    
    public JQueryPager getBudgetModelCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bmProcessId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmModelProcess.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			e.printStackTrace();
			return paginatedList;
		}

	}
}
