package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetModel.dao.BmModelProcessLogDao;
import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessLogManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmModelProcessLogManager")
public class BmModelProcessLogManagerImpl extends GenericManagerImpl<BmModelProcessLog, String> implements BmModelProcessLogManager {
    private BmModelProcessLogDao bmModelProcessLogDao;

    @Autowired
    public BmModelProcessLogManagerImpl(BmModelProcessLogDao bmModelProcessLogDao) {
        super(bmModelProcessLogDao);
        this.bmModelProcessLogDao = bmModelProcessLogDao;
    }
    
    public JQueryPager getBmModelProcessLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmModelProcessLogDao.getBmModelProcessLogCriteria(paginatedList,filters);
	}
}