package com.huge.ihos.inout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.inout.dao.SpecialSourceDao;
import com.huge.ihos.inout.model.SpecialSource;
import com.huge.ihos.inout.service.SpecialSourceManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("specialSourceManager")
public class SpecialSourceManagerImpl extends GenericManagerImpl<SpecialSource, Long> implements SpecialSourceManager {
    private SpecialSourceDao specialSourceDao;

    @Autowired
    public SpecialSourceManagerImpl(SpecialSourceDao specialSourceDao) {
        super(specialSourceDao);
        this.specialSourceDao = specialSourceDao;
    }
    
    public JQueryPager getSpecialSourceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return specialSourceDao.getSpecialSourceCriteria(paginatedList,filters);
	}

	@Override
	public String getCBStatus(String checkPeriod) {
		return specialSourceDao.getCBStatus(checkPeriod);
	}

	@Override
	public String getItemType(String itemId) {
		return specialSourceDao.getItemType(itemId);
	}

	@Override
	public List changeSpecialItemList(String itemTypeName) {
		return specialSourceDao.changeSpecialItemList(itemTypeName);
	}
}