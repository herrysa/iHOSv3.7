package com.huge.ihos.hr.hrPerson.service;


import java.util.List;

import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrPersonCurrentManager extends GenericManager<HrPersonCurrent, String> {
     public JQueryPager getHrPersonCurrentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 
      * @param ida personID数组
      * @param fileds 需要查询的字段
      * @return List<Object[]>
      */
     public List<Object[]> getHrPersonSomeFileds(String[] ida,String fileds);
     /**
	  *  检查锁的状态
	  * @param personId
	  * @param checkLockStates
	  * @return
	  */
	 public String checkLockHrPersonCurrent(String personId,String[] checkLockStates);
	 /**
	  * 本月过生日的员工
	  * @return List<HrPersonCurrent>
	  */
	 public List<HrPersonCurrent> getBirthThisMonthHrPersonList();
	 public Boolean personRemovable(String personIds);
	 /**
	  * 获取当前人员的personId列表
	  * @return
	  */
	 public List<String> getPersonIdList();
}