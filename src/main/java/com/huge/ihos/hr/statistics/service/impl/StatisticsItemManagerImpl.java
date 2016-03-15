package com.huge.ihos.hr.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.hr.statistics.dao.StatisticsItemDao;
import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.ihos.hr.statistics.model.StatisticsDetail;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.ihos.hr.statistics.service.StatisticsConditionManager;
import com.huge.ihos.hr.statistics.service.StatisticsItemManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("statisticsItemManager")
public class StatisticsItemManagerImpl extends GenericManagerImpl<StatisticsItem, String> implements StatisticsItemManager {
    private StatisticsItemDao statisticsItemDao;
    private StatisticsConditionManager statisticsConditionManager;

    @Autowired
    public StatisticsItemManagerImpl(StatisticsItemDao statisticsItemDao) {
        super(statisticsItemDao);
        this.statisticsItemDao = statisticsItemDao;
    }
    @Autowired
    public void setStatisticsConditionManager(StatisticsConditionManager statisticsConditionManager) {
		this.statisticsConditionManager = statisticsConditionManager;
	}
    public JQueryPager getStatisticsItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return statisticsItemDao.getStatisticsItemCriteria(paginatedList,filters);
	}
    public StatisticsDetail getStatisticsDetail(String statisticsCode){
    	return statisticsItemDao.getStatisticsDetail(statisticsCode);
    }
    public void deleteStatisticsItem(String[] ids){
    	for(int i=0;i<ids.length;i++){
    		String id=ids[i];
    		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		List<StatisticsCondition> statisticsConditions=new ArrayList<StatisticsCondition>();
    		filters.add(new PropertyFilter("EQS_parentItem.id",id));
    		statisticsConditions=statisticsConditionManager.getByFilters(filters);
    		if(statisticsConditions!=null&&statisticsConditions.size()>0){
    			int conditionLength=statisticsConditions.size();
    			String[] conditionIds=new String[conditionLength];
    			int conditionIdIndex=0;
    			for(StatisticsCondition statisticsCondition:statisticsConditions){
    				conditionIds[conditionIdIndex]=statisticsCondition.getId();
    				conditionIdIndex++;
    			}
    			statisticsConditionManager.deleteStatisticsConditionAndTarget(conditionIds);
    		}
    		this.remove(id);
    	}
    }
}