package com.huge.ihos.hr.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.statistics.dao.StatisticsTargetDao;
import com.huge.ihos.hr.statistics.model.StatisticsResultData;
import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.hr.statistics.service.StatisticsTargetManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.jfusionchart.data.CategoryDataset;
import com.jfusionchart.data.Dataset;
import com.jfusionchart.data.Dataset2D;
import com.jfusionchart.data.DefaultDataset;





@Service("statisticsTargetManager")
public class StatisticsTargetManagerImpl extends GenericManagerImpl<StatisticsTarget, String> implements StatisticsTargetManager {
    private StatisticsTargetDao statisticsTargetDao;

    @Autowired
    public StatisticsTargetManagerImpl(StatisticsTargetDao statisticsTargetDao) {
        super(statisticsTargetDao);
        this.statisticsTargetDao = statisticsTargetDao;
    }
    
    public JQueryPager getStatisticsTargetCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return statisticsTargetDao.getStatisticsTargetCriteria(paginatedList,filters);
	}
    @Override
    public void saveStatisticsTargetGridData(String gridAllDatas,String conditionId,Person person){
    	statisticsTargetDao.saveStatisticsTargetGridData(gridAllDatas, conditionId,person);
    }
    @SuppressWarnings("unchecked")
    @Override
    public Map<String,String> statisticsFullData(String itemId,String itemIdSecond,String mccKeyId) throws Exception{
    	List<StatisticsResultData> statisticsResultDataList=new ArrayList<StatisticsResultData>();
    	try{
    	if(itemIdSecond==null){
    		Object[] dataListObj=new Object[2];
    		dataListObj=statisticsTargetDao.statisticsFullData(itemId);
    		statisticsResultDataList=(List<StatisticsResultData>)dataListObj[0];
    		List<String> keyList=(List<String>)dataListObj[1];
        	Dataset dataset = new DefaultDataset();
        	dataListObj=new Object[2];
        	dataListObj=statisticsTargetDao.statisticsFullChartDataset(statisticsResultDataList,keyList);
        	dataset=(Dataset)dataListObj[0];
        	String jsonStr=(String)dataListObj[1];
        	return statisticsTargetDao.statisticsFullChartXMLMap(dataset, mccKeyId,jsonStr,"");
    	}else{
    		Object[] dataListObj=new Object[3];
    		dataListObj=statisticsTargetDao.statisticsFullData2D(itemId, itemIdSecond);
    		statisticsResultDataList=(List<StatisticsResultData>)dataListObj[0];
    		List<String> keyList=(List<String>)dataListObj[1];
			List<String> keySecondList=(List<String>)dataListObj[2];
    		Dataset2D<String> dataset  = new CategoryDataset<String>();
    		dataListObj=new Object[2];
    		dataListObj=statisticsTargetDao.statisticsFullChartDataset2D(statisticsResultDataList,keyList,keySecondList);
    		dataset=(Dataset2D<String>)dataListObj[0];
    		String jsonStr=(String)dataListObj[1];
    		return statisticsTargetDao.statisticsFullChartXMLMap2D(dataset, mccKeyId,jsonStr,"");
    	}
    	}catch(Exception e){
    		throw e;
    	}
    }
    @SuppressWarnings("unchecked")
	@Override
    public Map<String,String> statisticsFullDataByFilter(String itemId,String mccKeyId,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String snapCode) throws Exception{
    	try{
    	List<StatisticsResultData> statisticsResultDataList=new ArrayList<StatisticsResultData>();
    	Object[] dataListObj=new Object[3];
    	dataListObj=statisticsTargetDao.statisticsFullDataByFilter(itemId, deptIds,gridAllDatas,filterExpression,searchDateFrom,searchDateTo,snapCode);
    	statisticsResultDataList=(List<StatisticsResultData>)dataListObj[0];
		List<String> keyList=(List<String>)dataListObj[1];
		String subTitle=(String)dataListObj[2];
    	Dataset dataset = new DefaultDataset();
    	dataListObj=new Object[2];
    	dataListObj=statisticsTargetDao.statisticsFullChartDataset(statisticsResultDataList,keyList);
    	dataset=(Dataset)dataListObj[0];
    	String jsonStr=(String)dataListObj[1];
    	return statisticsTargetDao.statisticsFullChartXMLMap(dataset, mccKeyId,jsonStr,subTitle);
    	}catch(Exception e){
		throw e;
	}
    }
    @SuppressWarnings("unchecked")
	@Override
    public Map<String,String> statisticsFullData2DByFilter(String itemId,String itemIdSecond,String mccKeyId,String deptIds,String gridAllDatas,String filterExpression,String conditionIds,String conditionSecondIds,String searchDateFrom,String searchDateTo,String snapCode) throws Exception{
    	try{
    	List<StatisticsResultData> statisticsResultDataList=new ArrayList<StatisticsResultData>();
    	Object[] dataListObj=new Object[4];
    	dataListObj=statisticsTargetDao.statisticsFullData2DByFilter(itemId, itemIdSecond, deptIds, gridAllDatas, filterExpression, conditionIds, conditionSecondIds,searchDateFrom,searchDateTo,snapCode);
    	statisticsResultDataList=(List<StatisticsResultData>)dataListObj[0];
		List<String> keyList=(List<String>)dataListObj[1];
		List<String> keySecondList=(List<String>)dataListObj[2];
		String subTitle=(String)dataListObj[3];
    	Dataset2D<String> dataset  = new CategoryDataset<String>();
    	dataListObj=new Object[2];
    	dataListObj=statisticsTargetDao.statisticsFullChartDataset2D(statisticsResultDataList,keyList,keySecondList);
    	dataset=(Dataset2D<String>)dataListObj[0];
    	String jsonStr=(String)dataListObj[1];
		return statisticsTargetDao.statisticsFullChartXMLMap2D(dataset, mccKeyId,jsonStr,subTitle);
    	}catch(Exception e){
    		throw e;
    	}
    }
    @Override
    public Map<String,String> statisticsFullSingleFieldData(String fieldId,String mainTableId,String deptFK,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String shijianFK,String snapCode){
    	return statisticsTargetDao.statisticsFullSingleFieldData(fieldId, mainTableId, deptFK, deptIds, gridAllDatas, filterExpression,searchDateFrom,searchDateTo,shijianFK,snapCode);
    }
}