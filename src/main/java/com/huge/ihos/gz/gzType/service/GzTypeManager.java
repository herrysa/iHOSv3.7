package com.huge.ihos.gz.gzType.service;


import java.util.List;

import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzTypeManager extends GenericManager<GzType, String> {
     public JQueryPager getGzTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 获取当前用户的当前工资类别
      * @param userId
      * @return
      */
     public GzType getCurrentGzType(String userId);
     /**
      * 切换工资类别
      * @param userId
      * @param changeIdArr
      */
     public void changeCurrentGzType(String userId,String[] changeIdArr);
     /**
      * 删除工资类别，同时删除结账表中的相应记录
      * @param removeIds
      */
     public void removeGzType(List<String> removeIds);
     /**
      * 保存工资类别并初始化工资项
      * @param gzType
      * @param oper
      * @return
      */
     public GzType savaGzType(GzType gzType,String oper,Boolean entityIsNew);
     /**
      * 获取所有的工资类别(不包含系统)
      * @param containDisabled 是否包含停用类别
      * @return
      */
     public List<GzType> allGzTypes(Boolean containDisabled);
     /**
      * 获取所有可用工资类别（去除系统和停用）
      * @return
      */
     public List<GzType> getAllAvailable();
     /**
      * 获取所有可用工资类别（去除系统和停用），添加权限过滤
      * @param gzTypePriv 权限
      * @return
      */
     public List<GzType> getAllAvailable(String gzTypePriv);
}