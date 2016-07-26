package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetModel.dao.BmModelDeptDao;
import com.huge.ihos.bm.budgetModel.model.BmModelDept;
import com.huge.ihos.bm.budgetModel.service.BmModelDeptManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmModelDeptManager")
public class BmModelDeptManagerImpl extends GenericManagerImpl<BmModelDept, String> implements BmModelDeptManager {
    private BmModelDeptDao bmModelDeptDao;

    @Autowired
    public BmModelDeptManagerImpl(BmModelDeptDao bmModelDeptDao) {
        super(bmModelDeptDao);
        this.bmModelDeptDao = bmModelDeptDao;
    }
    
    public JQueryPager getBmModelDeptCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmModelDeptDao.getBmModelDeptCriteria(paginatedList,filters);
	}
}