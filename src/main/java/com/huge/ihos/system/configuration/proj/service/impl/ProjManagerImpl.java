package com.huge.ihos.system.configuration.proj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.proj.dao.ProjDao;
import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.ihos.system.configuration.proj.service.ProjManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("projManager")
public class ProjManagerImpl extends GenericManagerImpl<Proj, String> implements ProjManager {
    private ProjDao projDao;

    @Autowired
    public ProjManagerImpl(ProjDao projDao) {
        super(projDao);
        this.projDao = projDao;
    }
    
    public JQueryPager getProjCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return projDao.getProjCriteria(paginatedList,filters);
	}
}