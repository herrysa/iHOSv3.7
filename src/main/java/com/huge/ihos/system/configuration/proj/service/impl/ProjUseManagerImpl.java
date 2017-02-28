package com.huge.ihos.system.configuration.proj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.proj.dao.ProjUseDao;
import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.ihos.system.configuration.proj.service.ProjUseManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("projUseManager")
public class ProjUseManagerImpl extends GenericManagerImpl<ProjUse, String> implements ProjUseManager {
    private ProjUseDao projUseDao;

    @Autowired
    public ProjUseManagerImpl(ProjUseDao projUseDao) {
        super(projUseDao);
        this.projUseDao = projUseDao;
    }
    
    public JQueryPager getProjUseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return projUseDao.getProjUseCriteria(paginatedList,filters);
	}

	@Override
	public List<ProjUse> getAllEnabled() {
		return projUseDao.getAllEnabled();
	}
}