package com.huge.ihos.system.systemManager.busiprocess.service;


import java.util.List;

import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcess;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusiProcessManager extends GenericManager<BusiProcess, Long> {
     public JQueryPager getBusinessProcessCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     /**
      * 执行业务对应的存储过程
      * @param code 业务编码
      * @param args 需要传入process的参数[orgCode,copyCode,period,personId,detailId]
      */
     public void execBusinessProcess(String code,Object... args);
}