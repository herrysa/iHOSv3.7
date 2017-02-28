package com.huge.ihos.bm.bugetNorm.service.impl;

import java.util.List;
import com.huge.ihos.bm.bugetNorm.dao.BugetNormDao;
import com.huge.ihos.bm.bugetNorm.model.BugetNorm;
import com.huge.ihos.bm.bugetNorm.service.BugetNormManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bugetNormManager")
public class BugetNormManagerImpl extends GenericManagerImpl<BugetNorm, String> implements BugetNormManager {
    private BugetNormDao bugetNormDao;

    @Autowired
    public BugetNormManagerImpl(BugetNormDao bugetNormDao) {
        super(bugetNormDao);
        this.bugetNormDao = bugetNormDao;
    }
    
    public JQueryPager getBugetNormCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bugetNormDao.getBugetNormCriteria(paginatedList,filters);
	}
}