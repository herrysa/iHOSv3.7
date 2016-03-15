package com.huge.ihos.hr.hrPerson.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrPersonSnapManager extends GenericManager<HrPersonSnap, String> {
     public JQueryPager getHrPersonSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public HrPersonSnap save(HrPersonSnap hrPersonSnap, Person person,String operType, String changedAttrs);

	 public List<String> getHisSnapIds(String timestamp);
	 
	 /**
	  * 删除人员
      * 将人员的deleted字段设为true，同时会记录操作日志，并同步t_person表
	  * @param ids 被删除的人员snapId数组
	  * @param person 操作人
	  * @param operDate 操作时间
	  */
	 public void delete(String[] ids, Person person,Date operDate,boolean asyncData);
	 /**
	  * 启用/停用人员
	  * @param ids 被启用/停用的人员snapId数组
	  * @param person 操作人
	  * @param operDate 操作时间
	  * @param oper 操作类型，enable为启用,disable为停用
	  */
	 public void enableOrDsiable(String[] ids, Person person,Date operDate,String oper,boolean asyncData);
	 /**
	  * 通用的保存人员的方法
	  * 该方法会自动判断对于人员的更新是insert还是update，同时会记录操作日志，并同步t_person表
	  * @param hrPersonSnap 人员基本信息
	  * @param hrSubSets 人员附属子集信息
	  * @param operPerson 操作人
	  * @param operDate 操作时间
	  * @return
	  */
	 public HrPersonSnap saveHrPerson(HrPersonSnap hrPersonSnap,String hrSubSets,Person operPerson,Date operDate,boolean asyncData);
	 public HrPersonSnap saveHrPersonWithImage(HrPersonSnap hrPersonSnap,String hrSubSets,Person operPerson,Date operDate,boolean asyncData,String imagePath,HttpServletRequest req) throws Exception;
	 /**
	  * 批量修改人员
	  * @param snapIds 人员snapId数组
	  * @param columnMap 需要修改的列集合，每个元素是一个<key,value> 其中key是字段名，value是字段值
	  * @param operPerson 操作人
	  * @param operDate 操作时间
	  */
	 public void batchUpdateHrPerson(String[] snapIds,Map<String,Object> columnMap,Person operPerson,Date operDate,boolean asyncData);

	 public List<HrPersonSnap> getPersonListByHisTime(String timestamp);
	 
	 public void snycUpdatePerson(HrPersonSnap hrPersonSnap,String operType);
	 public void syncUpdateHrPerson(Person person,String snapCode,Person operPerson,Date date);
	 public void snycHrPerson(Person person,Person operPerson,Date date);
	 /**
	  * HrPersonSnap上锁
	  * @param personId
	  * @param lockState
	  */
	 public void lockHrPerson(String personId,String lockState);
	 /**
	  * HrPersonSnap解锁
	  * @param personId
	  * @param lockState
	  */
	 public void unlockHrPersonSnap(String personId,String lockState);
	 /**
	  *  检查锁的状态
	  * @param personId
	  * @param checkLockStates
	  * @return
	  */
	 public String checkLockHrPersonSnap(String personId,String[] checkLockStates);
	 /**
	  * 获取某一时间点人员Id和人员的Map
	  * @param timestamp
	  * @return
	  */
	 public Map<String, HrPersonSnap> getPersonIdPersonMap(String timestamp);
}