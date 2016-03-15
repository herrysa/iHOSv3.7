package com.huge.ihos.hr.trainExpense.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainExpense.model.TrainExpense;
import com.huge.ihos.hr.trainExpense.dao.TrainExpenseDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainExpenseDao")
public class TrainExpenseDaoHibernate extends GenericDaoHibernate<TrainExpense, String> implements TrainExpenseDao {

    public TrainExpenseDaoHibernate() {
        super(TrainExpense.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainExpenseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainExpense.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainExpenseCriteria", e);
			return paginatedList;
		}

	}
}
