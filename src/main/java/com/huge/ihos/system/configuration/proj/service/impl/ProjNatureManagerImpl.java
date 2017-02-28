package com.huge.ihos.system.configuration.proj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.proj.dao.ProjNatureDao;
import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.ihos.system.configuration.proj.service.ProjNatureManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("projNatureManager")
public class ProjNatureManagerImpl extends GenericManagerImpl<ProjNature, String> implements ProjNatureManager {
    private ProjNatureDao projNatureDao;

    @Autowired
    public ProjNatureManagerImpl(ProjNatureDao projNatureDao) {
        super(projNatureDao);
        this.projNatureDao = projNatureDao;
    }
    
    public JQueryPager getProjNatureCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return projNatureDao.getProjNatureCriteria(paginatedList,filters);
	}

	@Override
	public List<ProjNature> getAllEnabled() {
		return projNatureDao.getAllEnabled();
	}
}