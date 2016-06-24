package com.huge.ihos.bm.budgetUpdata.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetUpdata.dao.BudgetUpdataDao;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetUpdataManager")
public class BudgetUpdataManagerImpl extends GenericManagerImpl<BudgetUpdata, String> implements BudgetUpdataManager {
    private BudgetUpdataDao budgetUpdataDao;

    @Autowired
    public BudgetUpdataManagerImpl(BudgetUpdataDao budgetUpdataDao) {
        super(budgetUpdataDao);
        this.budgetUpdataDao = budgetUpdataDao;
    }
    
    public JQueryPager getBudgetUpdataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetUpdataDao.getBudgetUpdataCriteria(paginatedList,filters);
	}
}