package com.huge.ihos.hr.query.service;


import java.util.List;

import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface QueryCommonDetailManager extends GenericManager<QueryCommonDetail, String> {
     public JQueryPager getQueryCommonDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void saveQueryCommonDetailGridData(String gridAllDatas,String queryCommonId,Person person);
     public String queryHrPersonIds(String queryCommonId,String snapCode);
     /**
      * 获取查用查询sql
      * @return
      */
     public String getQueryCommonSql();
     /**
      * 获取常用查询结果
      * @return
      */
     public String getQueryCommonResult(String sql,String personId);
     /**
      * 获取常用查询sql
      * @return
      */
     public String getQueryCommonSql(String queryCommonId,String snapCode);
}