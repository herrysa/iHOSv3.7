package com.huge.ihos.material.deptapp.service;


import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptAppDLDetailManager extends GenericManager<DeptAppDLDetail, String> {
     public JQueryPager getDeptAppDLDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<DeptAppDLDetail> getDeptAppDLDetails(DeptApp deptApp,String invDictId);
     /**
      * 回写、更新/删除发放记录的出库单号
      * @param outNo 出库单号
      * @param type
      * @param deptAppId
      * @param deptAppDetailIds
      */
     public void setOutNoByInvMainOut(String outNo,String type,String deptAppId,String deptAppDetailIds);
}