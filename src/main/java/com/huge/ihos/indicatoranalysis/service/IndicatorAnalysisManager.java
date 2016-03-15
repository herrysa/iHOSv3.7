package com.huge.ihos.indicatoranalysis.service;


import java.util.List;

import com.huge.ihos.indicatoranalysis.model.IndicatorAnalysis;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface IndicatorAnalysisManager extends GenericManager<IndicatorAnalysis, String> {
     public JQueryPager getIndicatorAnalysisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	 /**
	  * 根据层级获取指标分析数据
	  * @param checkPeriod
	  * @param indicatorTypeCode
	  * @param level 层级
	  * @param oneLevel true:只获取本层的数据，false：获取从顶层至本层的数据
	  */
	 public List<IndicatorAnalysis> getByLevel(String checkPeriod,String indicatorTypeCode, Integer level,boolean oneLevel);
	 /**
	  * 根据指标获取该指标下的所有指标分析数据
	  * @param checkPeriod 期间
	  * @param indicatorId 指标Id
	  * @return
	  */
	 public List<IndicatorAnalysis> getById(String checkPeriod,String indicatorId);
	 /**
	  * 根据指标类型code获取指标分析数据
	  * @param checkPeriod 期间
	  * @param indicatorTypeCode 指标类型
	  * @return
	  */
	 public List<IndicatorAnalysis> getByTypeCode(String checkPeriod,String indicatorTypeCode);
	 /**
	  * 初始化本期间数据
	  * @param checkPeriod 期间
	  * @param indicatorTypeCode 指标类型
	  */
	 public void initIndicatorValue(String checkPeriod, String indicatorTypeCode);
	 /**
      * 根据期间计算指标分析数值
      * @param checkPeriod 期间
      * @param indicatorTypeCode 指标类型
      * @taskName 存储过程名
      */
	 public void calculateIndicatorValue(String checkPeriod,String indicatorTypeCode,String taskName);
	 
	 public boolean exeSp(String checkPeriod,String indicatorTypeCode,String taskName);
}