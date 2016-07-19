package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;

import com.huge.ihos.bm.budgetModel.dao.BmModelProcessDao;
import com.huge.ihos.bm.budgetModel.dao.BudgetModelDao;
import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessManager;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmModelProcessManager")
public class BmModelProcessManagerImpl extends GenericManagerImpl<BmModelProcess, String> implements BmModelProcessManager {
    private BmModelProcessDao bmModelProcessDao;

    @Autowired
    public BmModelProcessManagerImpl(BmModelProcessDao bmModelProcessDao) {
        super(bmModelProcessDao);
        this.bmModelProcessDao = bmModelProcessDao;
    }
    
    public JQueryPager getBudgetModelCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmModelProcessDao.getBudgetModelCriteria(paginatedList,filters);
	}
}