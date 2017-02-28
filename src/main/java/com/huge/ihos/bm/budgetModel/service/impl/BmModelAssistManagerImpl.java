package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetModel.dao.BmModelAssistDao;
import com.huge.ihos.bm.budgetModel.model.BmModelAssist;
import com.huge.ihos.bm.budgetModel.service.BmModelAssistManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmModelAssistManager")
public class BmModelAssistManagerImpl extends GenericManagerImpl<BmModelAssist, String> implements BmModelAssistManager {
    private BmModelAssistDao bmModelAssistDao;

    @Autowired
    public BmModelAssistManagerImpl(BmModelAssistDao bmModelAssistDao) {
        super(bmModelAssistDao);
        this.bmModelAssistDao = bmModelAssistDao;
    }
    
    public JQueryPager getBmModelAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmModelAssistDao.getBmModelAssistCriteria(paginatedList,filters);
	}
}