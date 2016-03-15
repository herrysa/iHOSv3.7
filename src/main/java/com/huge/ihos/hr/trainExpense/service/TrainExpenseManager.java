package com.huge.ihos.hr.trainExpense.service;


import java.util.List;
import com.huge.ihos.hr.trainExpense.model.TrainExpense;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainExpenseManager extends GenericManager<TrainExpense, String> {
     public JQueryPager getTrainExpenseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public TrainExpense saveTrainExpense(TrainExpense trainExpense,Boolean isEntityIsNew);
}