package com.huge.ihos.system.configuration.modelstatus.service;


import java.util.List;

import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ModelStatusManager extends GenericManager<ModelStatus, String> {
     public JQueryPager getModelStatusCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 结账
      * @param modelStatus
      * @return
      */
     public ModelStatus closeCount(ModelStatus modelStatus,String modelStatusType);
     public String getUsingPeriod(String modelId,String periodType);
     /**
      * 获取当前考核类型的最后结账的期间
      * @param modelId
      * @param periodType
      * @return
      */
	 public String getClosedPeriod(String modelId, String periodType);
	 /**
	  * 反结账，返回下一个待结账的期间
	  * @param periodType
	  * @param checkperiod
	  * @return
	  */
	 public String antiCount(String periodType, String checkperiod,String modelStatusType);
	 /**
	  * 根据登陆期间判断本年度期间是否已启用
	  * @param modelId 模块代码
	  * @param loginPeriod 期间
	  * @return false:未启用；true：已启用
	  */
	 public boolean isYearStarted(String modelId,String loginPeriod);
	 /**
	  * 判断登录期间是否启用
	  * @param modelId
	  * @param loginPeriod
	  * @return
	  */
	 public Boolean isYearMonthStarted(String modelId,String loginPeriod);
	 /**
	  *  当前登录期间是否结账
	  * @param modelId
	  * @param loginPeriod
	  * @return
	  */
	 public boolean isYearMothClosed(String modelId,String loginPeriod);
	 /**
	  * 工资结账
	  * @param id
	  * @param nextPeriod
	  * @return
	  */
	 public ModelStatus gzModelStatusClose(String id,String nextPeriod,String oper);
	 /**
	  * 工资类别使用状态
	  * @param gzTypeId
	  * @return anti反结账  closed已结账
	  */
	 public String gzTypeMSStatus(String gzTypeId);
	 /**
	  * 获得当前期间和类别下可用的ModelStatus
	  * @param typeId
	  * @param period
	  * @param subSystemCode
	  * @return
	  */
	 public ModelStatus getUsableModelStatus(String typeId,String period,String subSystemCode);
	 /**
	  * 获取当前工资类别第一个可用的ModelStatus
	  * @param gzTypeId
	  * @return
	  */
	 public ModelStatus getGzFirstUsableModelStatus(String gzTypeId);
	
}