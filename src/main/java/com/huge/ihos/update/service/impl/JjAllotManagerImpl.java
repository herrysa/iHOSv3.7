package com.huge.ihos.update.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.update.dao.JjAllotDao;
import com.huge.ihos.update.model.JjAllot;
import com.huge.ihos.update.service.JjAllotManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("jjAllotManager")
public class JjAllotManagerImpl extends GenericManagerImpl<JjAllot, Integer> implements JjAllotManager {
    private JjAllotDao jjAllotDao;

    @Autowired
    public JjAllotManagerImpl(JjAllotDao jjAllotDao) {
        super(jjAllotDao);
        this.jjAllotDao = jjAllotDao;
    }
    
    public JQueryPager getJjAllotCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return jjAllotDao.getJjAllotCriteria(paginatedList,filters);
	}

	@Override
	public String getCurrentItemName( String CurrentCheckPeriod) {
		return jjAllotDao.getCurrentItemName( CurrentCheckPeriod);
	}

	@Override
	public BigDecimal getAmountCompare(BigDecimal allot, String deptId,String checkPeriod) {
		return jjAllotDao.getAmountCompare(allot, deptId, checkPeriod);
	}

	@Override
	public BigDecimal getRealDeptAmount(String deptId, String checkPeriod) {
		return jjAllotDao.getRealDeptAmount(deptId, checkPeriod);
	}
}