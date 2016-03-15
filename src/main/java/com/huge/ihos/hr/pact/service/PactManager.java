package com.huge.ihos.hr.pact.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PactManager extends GenericManager<Pact, String> {
     public JQueryPager getPactCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 合同变更级联修改人员为离职
      * @param pactIdList 变动的合同Id集合
      * @param date 操作日期
      * @param person 操作人
      * @param changeType 变动类型
      */
     public void updatePersonByPactChange(List<String> pactIdList, Date date, Person person,String changeType,boolean asyncData);
     /**
	  * Pact上锁
	  * @param pactId
	  * @param lockState
	  */
	 public void lockPact(String pactId,String lockState);
	 /**
	  * Pact解锁
	  * @param pactId
	  * @param lockState
	  */
	 public void unlockPact(String pactId,String lockState);
	 /**
	  *  检查锁的状态
	  * @param pactId
	  * @param checkLockStates
	  * @return
	  */
	 public String checkLockPact(String pactId,String[] checkLockStates);
	 public void deletePact(List<String> delIds);
	 public Pact savePact(Pact pact,Person person);
}