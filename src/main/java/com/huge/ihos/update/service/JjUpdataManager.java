package com.huge.ihos.update.service;


import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.update.model.JjUpdata;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface JjUpdataManager extends GenericManager<JjUpdata, Long> {
     public JQueryPager getJjUpdataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public boolean isHaveUpdateRight(String personId);
     
     public boolean isUpdataed(String checkPeriod,String deptId);
     
 	 public boolean isExistUpdataed(String checkPeriod, String deptId);
 	
 	 public List<JjUpdata> findByDept(String checkPeriod , String deptId);
 	 /**
 	  * 继承上报数据
 	  * @param hisPeriod 历史期间
 	  * @param checkPeriod 当前期间
 	  * @param deptId 上报科室
 	  * @param defColumnIds 自定义列Id
 	  * @param itemName 上报项目
 	  * @person 操作人
 	  * @amount 是否继承金额
 	  * return true:继承成功；false：继承失败；null：没有历史数据
 	  */
	 public Boolean inheritJiUpData(String hisPeriod, String checkPeriod,String deptId, String defColumnNames, String itemName,Person person,Boolean amount) throws Exception;
}