package com.huge.ihos.accounting.kjyear.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.kjyear.dao.KjYearDao;
import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.ihos.accounting.kjyear.service.KjYearManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kjYearManager")
public class KjYearManagerImpl extends GenericManagerImpl<KjYear, String> implements KjYearManager {
    private KjYearDao kjYearDao;

    @Autowired
    public KjYearManagerImpl(KjYearDao kjYearDao) {
        super(kjYearDao);
        this.kjYearDao = kjYearDao;
    }
    
    public JQueryPager getKjYearCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kjYearDao.getKjYearCriteria(paginatedList,filters);
	}

	@Override
	public KjYear getKjyearByDate(String orgCode,String optDate) {
		return kjYearDao.getKjyearByDate(orgCode,optDate);
	}

	@Override
	public KjYear getKjYear(HashMap<String, String> environment) {
		return kjYearDao.getKjYear(environment);
	}
}