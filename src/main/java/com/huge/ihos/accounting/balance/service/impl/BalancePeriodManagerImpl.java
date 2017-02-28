package com.huge.ihos.accounting.balance.service.impl;

import java.util.HashMap;
import java.util.List;
import com.huge.ihos.accounting.balance.dao.BalancePeriodDao;
import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.ihos.accounting.balance.service.BalancePeriodManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("balancePeriodManager")
public class BalancePeriodManagerImpl extends GenericManagerImpl<BalancePeriod, String> implements BalancePeriodManager {
    private BalancePeriodDao balancePeriodDao;

    @Autowired
    public BalancePeriodManagerImpl(BalancePeriodDao balancePeriodDao) {
        super(balancePeriodDao);
        this.balancePeriodDao = balancePeriodDao;
    }
    
    public JQueryPager getBalancePeriodCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return balancePeriodDao.getBalancePeriodCriteria(paginatedList,filters);
	}

	@Override
	public List<BalancePeriod> getBalancePeriodByEnvironment(
			HashMap<String, String> environment) {
		return balancePeriodDao.getBalancePeriodByEnvironment(environment);
	}

	@Override
	public void updateMonthBalance(String orgCode, String copyCode,
			String kjYear, String month) {
		balancePeriodDao.updateMonthBalance(orgCode,copyCode,kjYear,month);
		
	}

}