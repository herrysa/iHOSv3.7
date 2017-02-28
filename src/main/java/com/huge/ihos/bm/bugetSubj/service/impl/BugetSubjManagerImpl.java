package com.huge.ihos.bm.bugetSubj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.bm.bugetSubj.dao.BugetSubjDao;
import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.ihos.bm.bugetSubj.service.BugetSubjManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bugetSubjManager")
public class BugetSubjManagerImpl extends GenericManagerImpl<BugetSubj, String> implements BugetSubjManager {
    private BugetSubjDao bugetSubjDao;

    @Autowired
    public BugetSubjManagerImpl(BugetSubjDao bugetSubjDao) {
        super(bugetSubjDao);
        this.bugetSubjDao = bugetSubjDao;
    }
    
    public JQueryPager getBugetSubjCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bugetSubjDao.getBugetSubjCriteria(paginatedList,filters);
	}
}