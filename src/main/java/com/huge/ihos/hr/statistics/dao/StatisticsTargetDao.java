package com.huge.ihos.hr.statistics.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.statistics.model.StatisticsResultData;
import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.jfusionchart.data.Dataset;
import com.jfusionchart.data.Dataset2D;
/**
 * An interface that provides a data management interface to the StatisticsTarget table.
 */
public interface StatisticsTargetDao extends GenericDao<StatisticsTarget, String> {
	public JQueryPager getStatisticsTargetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	  /**
     * 统计指标jqgrid保存数据
     * @param gridAllDatas jqgrid JSON数据
     * @param conditionId 统计条件ID
     * @param person 修改人
     */
	public void saveStatisticsTargetGridData(String gridAllDatas,String conditionId,Person person);
	/**
	 * 统计项查询数据
	 * @param itemId 统计项Id
	 * @return  StatisticsResultData List集合
	 */
	public Object[] statisticsFullData(String itemId);
	/**
	 * 图数据集
	 * @param statisticsResultDataList
	 * @return 图数据集
	 */
	public Object[] statisticsFullChartDataset(List<StatisticsResultData> statisticsResultDataList,List<String> keyList);
	/**
	 * 图Xml数据
	 * @param dataSet
	 * @param mccKeyId
	 * @return 图Xml数据
	 */
	public Map<String,String> statisticsFullChartXMLMap(Dataset dataSet,String mccKeyId,String jsonStr,String subTitle) throws Exception;
	/**
	 * 
	 * @param itemId
	 * @param itemIdSecond
	 * @return
	 */
	public Object[] statisticsFullData2D(String itemId,String itemIdSecond) throws Exception;
	/**
	 * 
	 * @param statisticsResultDataList
	 * @return
	 */
	public Object[] statisticsFullChartDataset2D(List<StatisticsResultData> statisticsResultDataList,List<String> keyList,List<String> keySecondList);
	/**
	 * 
	 * @param dataSet
	 * @param mccKeyId
	 * @return
	 */
	public Map<String,String> statisticsFullChartXMLMap2D(Dataset2D<String> dataSet,String mccKeyId,String jsonStr,String subTitle)  throws Exception;
	/**
	 * 常用统计
	 * @param itemId
	 * @param deptIds
	 * @param gridAllDatas
	 * @param filterExpression
	 * @param searchDateFrom
	 * @param searchDateTo
	 * @param snapCode
	 * @return
	 * @throws Exception
	 */
	public Object[] statisticsFullDataByFilter(String itemId,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String snapCode)throws Exception;
	/**
	 * 二维统计
	 * @param itemId
	 * @param itemIdSecond
	 * @param deptIds
	 * @param gridAllDatas
	 * @param filterExpression
	 * @param conditionIds
	 * @param conditionSecondIds
	 * @param searchDateFrom
	 * @param searchDateTo
	 * @param snapCode
	 * @return
	 * @throws Exception
	 */
	public Object[] statisticsFullData2DByFilter(String itemId,String itemIdSecond,String deptIds,String gridAllDatas,String filterExpression,String conditionIds,String conditionSecondIds,String searchDateFrom,String searchDateTo,String snapCode)throws Exception;
	public Map<String,String> statisticsFullSingleFieldData(String fieldId,String mainTableId,String deptFK,String deptIds,String gridAllDatas,String filterExpression,String searchDateFrom,String searchDateTo,String shijianFK,String snapCode);
}