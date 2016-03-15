package com.huge.ihos.strategy.service.impl;

import java.util.List;
import com.huge.ihos.strategy.dao.StrategyDao;
import com.huge.ihos.strategy.model.Strategy;
import com.huge.ihos.strategy.service.StrategyManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("strategyManager")
public class StrategyManagerImpl extends GenericManagerImpl<Strategy, Integer> implements StrategyManager {
    private StrategyDao strategyDao;

    @Autowired
    public StrategyManagerImpl(StrategyDao strategyDao) {
        super(strategyDao);
        this.strategyDao = strategyDao;
    }
    
    public JQueryPager getStrategyCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return strategyDao.getStrategyCriteria(paginatedList,filters);
	}
}