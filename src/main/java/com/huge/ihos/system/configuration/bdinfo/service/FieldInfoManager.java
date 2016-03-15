package com.huge.ihos.system.configuration.bdinfo.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface FieldInfoManager extends GenericManager<FieldInfo, String> {
     public JQueryPager getFieldInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 获取FieldInfo对于sql语句中的详细信息
      * @param fieldInfo
      * @return
      */
     public Map<String, String> getDBSqlInfoMap(FieldInfo fieldInfo);
}