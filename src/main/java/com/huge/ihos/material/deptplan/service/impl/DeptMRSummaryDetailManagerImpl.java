package com.huge.ihos.material.deptplan.service.impl;

import java.util.List;
import com.huge.ihos.material.deptplan.dao.DeptMRSummaryDetailDao;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("deptMRSummaryDetailManager")
public class DeptMRSummaryDetailManagerImpl extends GenericManagerImpl<DeptMRSummaryDetail, String> implements DeptMRSummaryDetailManager {
    private DeptMRSummaryDetailDao deptMRSummaryDetailDao;

    @Autowired
    public DeptMRSummaryDetailManagerImpl(DeptMRSummaryDetailDao deptMRSummaryDetailDao) {
        super(deptMRSummaryDetailDao);
        this.deptMRSummaryDetailDao = deptMRSummaryDetailDao;
    }
    
    public JQueryPager getDeptMRSummaryDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptMRSummaryDetailDao.getDeptMRSummaryDetailCriteria(paginatedList,filters);
	}
}