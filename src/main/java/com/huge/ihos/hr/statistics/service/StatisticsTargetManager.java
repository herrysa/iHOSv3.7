package com.huge.ihos.hr.statistics.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StatisticsTargetManager extends GenericManager<StatisticsTarget, String> {
     public JQueryPager getStatisticsTargetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 统计指标jqgrid保存数据
      * @param gridAllDatas jqgrid JSON数据
      * @param conditionId 统计条件ID
      * @param person 修改人
      */
     public void saveStatisticsTargetGridData(String gridAllDatas,String conditionId,Person person);
     /**
      * XML统计数据
      * @param itemId 统计项Id
      * @param itemIdSecond 二维统计辅统计项ID
      * @param mccKeyId 图形定义编码
      * @return Map<String,String> XML统计数据
      */
     public Map<String,String> statisticsFullData(String itemId,String itemIdSecond,String mccKeyId) throws Exception;
     /**
      * 常用统计(一个统计项)
      * @param itemId 统计项ID
      * @param mccKeyId 图形定义编号
      * @param deptIds 部门ID串
      * @param gridAllDatas 过滤条件json串
      * @param filterExpression 过滤条件公式
      * @param searchDateFrom 
      * @param searchDateTo
      * @param snapCode
      * @return XML的Map集合
      * @throws Exception
      */
     public Map<String,String> statisticsFullDataByFilter(String itemId,String mccKeyId,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String snapCode) throws Exception;
     /**
      * 二维统计(两个统计项)
      * @param itemId 统计项ID
      * @param itemIdSecond 第二个统计项ID
      * @param mccKeyId 图形定义编号
      * @param deptIds 部门ID串
      * @param gridAllDatas 过滤条件json串
      * @param filterExpression 过滤条件公式
      * @param conditionIds 
      * @param conditionSecondIds
      * @param searchDateFrom
      * @param searchDateTo
      * @param snapCode
      * @return
      * @throws Exception
      */
     public Map<String,String> statisticsFullData2DByFilter(String itemId,String itemIdSecond,String mccKeyId,String deptIds,String gridAllDatas,String filterExpression,String conditionIds,String conditionSecondIds,String searchDateFrom,String searchDateTo,String snapCode) throws Exception;
     public Map<String,String> statisticsFullSingleFieldData(String fieldId,String mainTableId,String deptFK,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String shijianFK,String snapCode);
}