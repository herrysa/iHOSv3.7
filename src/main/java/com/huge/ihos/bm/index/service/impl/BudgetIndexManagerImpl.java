package com.huge.ihos.bm.index.service.impl;

import java.util.List;
import com.huge.ihos.bm.index.dao.BudgetIndexDao;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.service.BudgetIndexManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("budgetIndexManager")
public class BudgetIndexManagerImpl extends GenericManagerImpl<BudgetIndex, String> implements BudgetIndexManager {
    private BudgetIndexDao budgetIndexDao;

    @Autowired
    public BudgetIndexManagerImpl(BudgetIndexDao budgetIndexDao) {
        super(budgetIndexDao);
        this.budgetIndexDao = budgetIndexDao;
    }
    
    public JQueryPager getBudgetIndexCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return budgetIndexDao.getBudgetIndexCriteria(paginatedList,filters);
	}
}