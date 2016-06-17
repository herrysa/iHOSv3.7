package com.huge.ihos.bm.budgetType.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetType.dao.BudgetTypeDao;
import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.ihos.bm.budgetType.service.BudgetTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetTypeManager")
public class BudgetTypeManagerImpl extends GenericManagerImpl<BudgetType, String> implements BudgetTypeManager {
    private BudgetTypeDao budgetTypeDao;

    @Autowired
    public BudgetTypeManagerImpl(BudgetTypeDao budgetTypeDao) {
        super(budgetTypeDao);
        this.budgetTypeDao = budgetTypeDao;
    }
    
    public JQueryPager getBudgetTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetTypeDao.getBudgetTypeCriteria(paginatedList,filters);
	}
}