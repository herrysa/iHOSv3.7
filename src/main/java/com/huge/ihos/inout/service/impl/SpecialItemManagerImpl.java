package com.huge.ihos.inout.service.impl;

import java.util.List;
import com.huge.ihos.inout.dao.SpecialItemDao;
import com.huge.ihos.inout.model.SpecialItem;
import com.huge.ihos.inout.service.SpecialItemManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("specialItemManager")
public class SpecialItemManagerImpl extends GenericManagerImpl<SpecialItem, String> implements SpecialItemManager {
    private SpecialItemDao specialItemDao;

    @Autowired
    public SpecialItemManagerImpl(SpecialItemDao specialItemDao) {
        super(specialItemDao);
        this.specialItemDao = specialItemDao;
    }
    
    public JQueryPager getSpecialItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return specialItemDao.getSpecialItemCriteria(paginatedList,filters);
	}

}