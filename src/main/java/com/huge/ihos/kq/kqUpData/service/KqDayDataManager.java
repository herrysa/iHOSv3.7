package com.huge.ihos.kq.kqUpData.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqDayDataManager extends GenericManager<KqDayData, String> {
     public JQueryPager getKqDayDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
    /**
     * 
     * @param columns
     * @param lastPeriod
     * @param sqlFilterList
     * @param sqlOrderList
     * @return
     */
     public List<Map<String,Object>> getKqDayDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);
     /**
      * 需要汇总的日上报表列
      * @param curPeriod
      * @param kqTypeId
      * @param curDeptId
      * @return
      */
     public List<Map<String, Object>> getKqSumaryDayData(String curPeriod,String kqTypeId,String curDeptId);
     
     public Boolean getKqDayDataChecked(String period,String kqType,String branchCode,String status);
}