package com.huge.ihos.indicatoranalysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.indicatoranalysis.dao.IndicatorTypeDao;
import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.ihos.indicatoranalysis.service.IndicatorTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("indicatorTypeManager")
public class IndicatorTypeManagerImpl extends GenericManagerImpl<IndicatorType, String> implements IndicatorTypeManager {
    private IndicatorTypeDao indicatorTypeDao;

    @Autowired
    public IndicatorTypeManagerImpl(IndicatorTypeDao indicatorTypeDao) {
        super(indicatorTypeDao);
        this.indicatorTypeDao = indicatorTypeDao;
    }
    
    public JQueryPager getIndicatorTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return indicatorTypeDao.getIndicatorTypeCriteria(paginatedList,filters);
	}
}