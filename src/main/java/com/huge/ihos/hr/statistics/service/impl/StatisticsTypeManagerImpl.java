package com.huge.ihos.hr.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.hr.statistics.dao.StatisticsTypeDao;
import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.ihos.hr.statistics.service.StatisticsTypeManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("statisticsTypeManager")
public class StatisticsTypeManagerImpl extends GenericManagerImpl<StatisticsType, String> implements StatisticsTypeManager {
    private StatisticsTypeDao statisticsTypeDao;

    @Autowired
    public StatisticsTypeManagerImpl(StatisticsTypeDao statisticsTypeDao) {
        super(statisticsTypeDao);
        this.statisticsTypeDao = statisticsTypeDao;
    }
    
    public JQueryPager getStatisticsTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return statisticsTypeDao.getStatisticsTypeCriteria(paginatedList,filters);
	}
    @Override
    public StatisticsType saveStatisticsType(StatisticsType statisticsType){
    	 statisticsType=this.save(statisticsType);
		 List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		 filters.add(new PropertyFilter("EQS_parentType.id", statisticsType.getId()));
		 List<StatisticsType> statisticsTypes=new ArrayList<StatisticsType>();
		 statisticsTypes=this.getByFilters(filters);
		 if(statisticsTypes!=null&&statisticsTypes.size()>0){
			 statisticsType.setLeaf(false);
		 }else{
			 statisticsType.setLeaf(true);
		 }
		 statisticsType=this.save(statisticsType);
		 if(statisticsType.getParentType()!=null){
			 StatisticsType statisticsTypeParent=new StatisticsType();
			 statisticsTypeParent=this.get(statisticsType.getParentType().getId());
			 statisticsTypeParent.setLeaf(false);
			 this.save(statisticsTypeParent);
		 }
		 return statisticsType;
    }
    @Override
    public void deleteStatisticsType(String[] ids){
    	for(int i=0;i<ids.length;i++){
    		String id=ids[i];
    		StatisticsType statisticsType=new StatisticsType();
    		statisticsType=this.get(id);
    		if(statisticsType.getParentType()!=null){
    			statisticsType=this.get(statisticsType.getParentType().getId());
    			statisticsType.setLeaf(true);
    			this.save(statisticsType);
    		}
    		this.remove(id);
    	}
    }
}