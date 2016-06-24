package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetModel.dao.BudgetModelDao;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetModelManager")
public class BudgetModelManagerImpl extends GenericManagerImpl<BudgetModel, String> implements BudgetModelManager {
    private BudgetModelDao budgetModelDao;

    @Autowired
    public BudgetModelManagerImpl(BudgetModelDao budgetModelDao) {
        super(budgetModelDao);
        this.budgetModelDao = budgetModelDao;
    }
    
    public JQueryPager getBudgetModelCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetModelDao.getBudgetModelCriteria(paginatedList,filters);
	}
}