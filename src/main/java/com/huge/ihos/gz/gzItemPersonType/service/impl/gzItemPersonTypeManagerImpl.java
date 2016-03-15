package com.huge.ihos.gz.gzItemPersonType.service.impl;

import java.util.List;

import com.huge.ihos.gz.gzItemPersonType.dao.gzItemPersonTypeDao;
import com.huge.ihos.gz.gzItemPersonType.model.GzItemPersonType;
import com.huge.ihos.gz.gzItemPersonType.service.gzItemPersonTypeManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzItemPersonTypeManager")
public class gzItemPersonTypeManagerImpl extends GenericManagerImpl<GzItemPersonType, String> implements gzItemPersonTypeManager {
    private gzItemPersonTypeDao gzItemPersonTypeDao;

    @Autowired
    public gzItemPersonTypeManagerImpl(gzItemPersonTypeDao gzItemPersonTypeDao) {
        super(gzItemPersonTypeDao);
        this.gzItemPersonTypeDao = gzItemPersonTypeDao;
    }
    
    public JQueryPager getgzItemPersonTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzItemPersonTypeDao.getgzItemPersonTypeCriteria(paginatedList,filters);
	}
}