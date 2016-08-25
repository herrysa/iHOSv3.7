package com.huge.ihos.bm.budgetModel.service.impl;

import java.util.List;
import com.huge.ihos.bm.budgetModel.dao.BmDepartmentDao;
import com.huge.ihos.bm.budgetModel.model.BmDepartment;
import com.huge.ihos.bm.budgetModel.service.BmDepartmentManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bmDepartmentManager")
public class BmDepartmentManagerImpl extends GenericManagerImpl<BmDepartment, String> implements BmDepartmentManager {
    private BmDepartmentDao bmDepartmentDao;

    @Autowired
    public BmDepartmentManagerImpl(BmDepartmentDao bmDepartmentDao) {
        super(bmDepartmentDao);
        this.bmDepartmentDao = bmDepartmentDao;
    }
    
    public JQueryPager getBmDepartmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bmDepartmentDao.getBmDepartmentCriteria(paginatedList,filters);
	}
}