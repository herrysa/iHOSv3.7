package com.huge.ihos.kq.kqType.service;


import java.util.List;

import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqTypeManager extends GenericManager<KqType, String> {
     public JQueryPager getKqTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public KqType getNowKqType();
     /**
      * 获取所有的考勤类别(不包含系统)
      * @param containDisabled 是否包含停用类别
      * @return
      */
     public List<KqType> allKqTypes(Boolean containDisable);
     /**
      * 
      * @param kqType
      * @param oper
      * @return
      */
     public KqType saveKqType(KqType kqType,String oper);
     /**
	  * 考勤结账
	  * @param id
	  * @param nextPeriod
	  * @return
	  */
	 public ModelStatus kqModelStatusClose(String id,String nextPeriod,String oper);
	 /**
	  * 获取所有可用的考勤类别（去除停用和系统）
	  * @return
	  */
	 public List<KqType> getAllAvailable();
	 /**
	  * 获取所有可用的考勤类别（去除停用和系统），添加权限过滤
	  * @param kqTypePriv
	  * @return
	  */
	 public List<KqType> getAllAvailable(String kqTypePriv);
	 

}