package com.huge.ihos.nursescore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.nursescore.dao.MonthNurseDao;
import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.ihos.nursescore.service.MonthNurseManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("monthNurseManager")
public class MonthNurseManagerImpl extends GenericManagerImpl<MonthNurse, Long> implements MonthNurseManager {
    private MonthNurseDao monthNurseDao;

    @Autowired
    public MonthNurseManagerImpl(MonthNurseDao monthNurseDao) {
        super(monthNurseDao);
        this.monthNurseDao = monthNurseDao;
    }
    
    public JQueryPager getMonthNurseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return monthNurseDao.getMonthNurseCriteria(paginatedList,filters);
	}

	@Override
	public List<MonthNurse> getByCheckPeriodAndDept(String checkPeriod,
			String deptId) {
		return monthNurseDao.getByCheckPeriodAndDept(checkPeriod, deptId);
	}
}