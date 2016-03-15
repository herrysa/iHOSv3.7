package com.huge.ihos.system.configuration.bdinfo.service;


import java.util.List;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BdInfoManager extends GenericManager<BdInfo, String> {
     public JQueryPager getBdInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public BdInfo findByTableName(String tableName);
     /**
      * 建表语句
      * @param tableName 表名
      * @return
      */
     public String createTableSql(String tableName);
     /**
      * 根据数据库列初始化fieldInfo
      * @param bdInfo
      * @param entityIsNew
      * @param oper
      * @return
      */
     public BdInfo saveBdInfo(BdInfo bdInfo,Boolean entityIsNew,String oper);
}