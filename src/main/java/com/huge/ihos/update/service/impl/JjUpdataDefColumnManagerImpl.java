package com.huge.ihos.update.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.update.dao.JjUpdataDefColumnDao;
import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.ihos.update.service.JjUpdataDefColumnManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("jjUpdataDefColumnManager")
public class JjUpdataDefColumnManagerImpl extends GenericManagerImpl<JjUpdataDefColumn, Long> implements JjUpdataDefColumnManager {
    private JjUpdataDefColumnDao jjUpdataDefColumnDao;

    @Autowired
    public JjUpdataDefColumnManagerImpl(JjUpdataDefColumnDao jjUpdataDefColumnDao) {
        super(jjUpdataDefColumnDao);
        this.jjUpdataDefColumnDao = jjUpdataDefColumnDao;
    }
    
    public JQueryPager getJjUpdataDefColumnCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return jjUpdataDefColumnDao.getJjUpdataDefColumnCriteria(paginatedList,filters);
	}

	@Override
	public List<JjUpdataDefColumn> getEnabledByOrder() {
		return jjUpdataDefColumnDao.getEnabledByOrder();
	}
}