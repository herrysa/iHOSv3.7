package com.huge.ihos.bm.budgetAssistType.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetAssistType.dao.BudgetAssistTypeDao;
import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.ihos.bm.budgetAssistType.service.BudgetAssistTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetAssistTypeManager")
public class BudgetAssistTypeManagerImpl extends GenericManagerImpl<BudgetAssistType, String> implements BudgetAssistTypeManager {
    private BudgetAssistTypeDao budgetAssistTypeDao;

    @Autowired
    public BudgetAssistTypeManagerImpl(BudgetAssistTypeDao budgetAssistTypeDao) {
        super(budgetAssistTypeDao);
        this.budgetAssistTypeDao = budgetAssistTypeDao;
    }
    
    public JQueryPager getBudgetAssistTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetAssistTypeDao.getBudgetAssistTypeCriteria(paginatedList,filters);
	}
}