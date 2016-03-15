package com.huge.ihos.hr.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.hr.statistics.dao.StatisticsConditionDao;
import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.hr.statistics.service.StatisticsConditionManager;
import com.huge.ihos.hr.statistics.service.StatisticsTargetManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("statisticsConditionManager")
public class StatisticsConditionManagerImpl extends GenericManagerImpl<StatisticsCondition, String> implements StatisticsConditionManager {
    private StatisticsConditionDao statisticsConditionDao;
    private StatisticsTargetManager statisticsTargetManager;

    @Autowired
    public StatisticsConditionManagerImpl(StatisticsConditionDao statisticsConditionDao) {
        super(statisticsConditionDao);
        this.statisticsConditionDao = statisticsConditionDao;
    }
    @Autowired
    public void setStatisticsTargetManager(StatisticsTargetManager statisticsTargetManager) {
		this.statisticsTargetManager = statisticsTargetManager;
	}
    public JQueryPager getStatisticsConditionCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return statisticsConditionDao.getStatisticsConditionCriteria(paginatedList,filters);
	}
    @Override
    public void deleteStatisticsConditionAndTarget(String[] ids){
    	for(int i=0;i<ids.length;i++){
    		String id=ids[i];
    		List<StatisticsTarget> statisticsTargets=new ArrayList<StatisticsTarget>();
    		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_parentCondition.id",id));
    		statisticsTargets=statisticsTargetManager.getByFilters(filters);
    		if(statisticsTargets!=null&&statisticsTargets.size()>0){
    			for(StatisticsTarget statisticsTarget:statisticsTargets){
    				statisticsTargetManager.remove(statisticsTarget.getId());
    			}
    		}
    		this.remove(id);
    	}
    }
}