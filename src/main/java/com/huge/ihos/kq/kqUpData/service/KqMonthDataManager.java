package com.huge.ihos.kq.kqUpData.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqMonthDataManager extends GenericManager<KqMonthData, String> {
     public JQueryPager getKqMonthDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 
      * @param columns
      * @param lastPeriod
      * @param sqlFilterList
      * @param sqlOrderList
      * @return
      */
     public List<Map<String,Object>> getKqMonthDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);

     
}