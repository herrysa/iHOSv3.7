package com.huge.ihos.formula.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.UpItemDao;
import com.huge.ihos.formula.model.UpItem;
import com.huge.ihos.formula.service.UpItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("upItemManager")
public class UpItemManagerImpl extends GenericManagerImpl<UpItem, Long> implements UpItemManager {
    private UpItemDao upItemDao;

    @Autowired
    public UpItemManagerImpl(UpItemDao upItemDao) {
        super(upItemDao);
        this.upItemDao = upItemDao;
    }
    
    public JQueryPager getUpItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return upItemDao.getUpItemCriteria(paginatedList,filters);
	}

	@Override
	public List<UpItem> getUpItemsByDept(String deptId,String upItemClass) {
		return upItemDao.getUpItemsByDept(deptId,upItemClass);
	}

	@Override
	public boolean validataUpCost(Long upItemId) {
		return upItemDao.validataUpCost(upItemId);
	}
}