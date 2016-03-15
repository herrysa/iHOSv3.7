package com.huge.ihos.hr.trainExpense.dao;


import java.util.List;

import com.huge.ihos.hr.trainExpense.model.TrainExpense;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the TrainExpense table.
 */
public interface TrainExpenseDao extends GenericDao<TrainExpense, String> {
	public JQueryPager getTrainExpenseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}