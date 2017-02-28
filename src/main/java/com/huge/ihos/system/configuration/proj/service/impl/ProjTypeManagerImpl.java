package com.huge.ihos.system.configuration.proj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.proj.dao.ProjTypeDao;
import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.ihos.system.configuration.proj.service.ProjTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("projTypeManager")
public class ProjTypeManagerImpl extends GenericManagerImpl<ProjType, String> implements ProjTypeManager {
    private ProjTypeDao projTypeDao;

    @Autowired
    public ProjTypeManagerImpl(ProjTypeDao projTypeDao) {
        super(projTypeDao);
        this.projTypeDao = projTypeDao;
    }
    
    public JQueryPager getProjTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return projTypeDao.getProjTypeCriteria(paginatedList,filters);
	}

	@Override
	public String pyCode(String string) {
		 return projTypeDao.getPyCodes(string);
	}

	@Override
	public List<ProjType> getAllEnabled() {
		return projTypeDao.getAllEnabled();
	}
}