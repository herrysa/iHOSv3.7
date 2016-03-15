package com.huge.ihos.hr.trainExpense.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainExpense.dao.TrainExpenseDao;
import com.huge.ihos.hr.trainExpense.model.TrainExpense;
import com.huge.ihos.hr.trainExpense.service.TrainExpenseManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainExpenseManager")
public class TrainExpenseManagerImpl extends GenericManagerImpl<TrainExpense, String> implements TrainExpenseManager {
    private TrainExpenseDao trainExpenseDao;
    private BillNumberManager billNumberManager;

    @Autowired
    public TrainExpenseManagerImpl(TrainExpenseDao trainExpenseDao) {
        super(trainExpenseDao);
        this.trainExpenseDao = trainExpenseDao;
    }
    @Autowired
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getTrainExpenseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainExpenseDao.getTrainExpenseCriteria(paginatedList,filters);
	}
    @Override
    public TrainExpense saveTrainExpense(TrainExpense trainExpense,Boolean isEntityIsNew){
    	if(isEntityIsNew){
			trainExpense.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_TRAIN_EXPENSE,trainExpense.getYearMonth()));
		}
    	trainExpense=trainExpenseDao.save(trainExpense);
    	return trainExpense;
    }
}