package com.huge.ihos.bm.bugetSubj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.bm.bugetSubj.dao.BugetAcctMapDao;
import com.huge.ihos.bm.bugetSubj.model.BugetAcctMap;
import com.huge.ihos.bm.bugetSubj.service.BugetAcctMapManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bugetAcctMapManager")
public class BugetAcctMapManagerImpl extends GenericManagerImpl<BugetAcctMap, String> implements BugetAcctMapManager {
    private BugetAcctMapDao bugetAcctMapDao;

    @Autowired
    public BugetAcctMapManagerImpl(BugetAcctMapDao bugetAcctMapDao) {
        super(bugetAcctMapDao);
        this.bugetAcctMapDao = bugetAcctMapDao;
    }
    
    public JQueryPager getBugetAcctMapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bugetAcctMapDao.getBugetAcctMapCriteria(paginatedList,filters);
	}
}