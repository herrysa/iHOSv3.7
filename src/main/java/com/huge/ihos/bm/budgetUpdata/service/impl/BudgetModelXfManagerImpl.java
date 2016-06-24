package com.huge.ihos.bm.budgetUpdata.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetUpdata.dao.BudgetModelXfDao;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.service.BudgetModelXfManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetModelXfManager")
public class BudgetModelXfManagerImpl extends GenericManagerImpl<BudgetModelXf, String> implements BudgetModelXfManager {
    private BudgetModelXfDao budgetModelXfDao;

    @Autowired
    public BudgetModelXfManagerImpl(BudgetModelXfDao budgetModelXfDao) {
        super(budgetModelXfDao);
        this.budgetModelXfDao = budgetModelXfDao;
    }
    
    public JQueryPager getBudgetModelXfCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetModelXfDao.getBudgetModelXfCriteria(paginatedList,filters);
	}
}