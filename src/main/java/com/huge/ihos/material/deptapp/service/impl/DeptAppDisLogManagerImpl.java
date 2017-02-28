package com.huge.ihos.material.deptapp.service.impl;

import java.util.List;
import com.huge.ihos.material.deptapp.dao.DeptAppDisLogDao;
import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.ihos.material.deptapp.service.DeptAppDisLogManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("deptAppDisLogManager")
public class DeptAppDisLogManagerImpl extends GenericManagerImpl<DeptAppDisLog, String> implements DeptAppDisLogManager {
    private DeptAppDisLogDao deptAppDisLogDao;

    @Autowired
    public DeptAppDisLogManagerImpl(DeptAppDisLogDao deptAppDisLogDao) {
        super(deptAppDisLogDao);
        this.deptAppDisLogDao = deptAppDisLogDao;
    }
    
    public JQueryPager getDeptAppDisLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptAppDisLogDao.getDeptAppDisLogCriteria(paginatedList,filters);
	}
}