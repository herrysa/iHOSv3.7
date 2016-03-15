package com.huge.ihos.hr.hrDeptment.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDepartmentSnapManager extends GenericManager<HrDepartmentSnap, String> {
     public JQueryPager getHrDepartmentSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
	 /**
	  * 获取历史时间的部门snapId集合
	  * @param hisTime 历史时间
	  * @return
	  */
	 public List<String> getHisSnapIdList(String timestamp);
	 /**
	  * 获取所有下级历史部门
	  * @param snapId 部门snapId
	  * @param hisTime 历史时间
	  * @return
	  */
	 public List<HrDepartmentSnap> getAllDescendants(String snapId,String hisTime);
	 /**
      * 删除部门
      * 该方法会自动判断是否真正的删除，若为真正的删除，一并删除附属子集信息；否则将deleted字段设为true
      * 同时会记录操作日志，并同步t_department
      * @param ids 被删除的部门snapId数组
      * @param person 操作人
      * @param operDate 操作时间
      */
	 public void delete(String[] ids, Person person,Date operDate,boolean asyncData);
	 /**
	  * 通用的保存部门的方法
	  * 该方法会自动判断对于部门的更新是insert还是update，同时会记录操作日志，并同步t_department表
	  * @param hrDepartmentSnap 部门信息
	  * @param hrSubSets 部门附属子集信息
	  * @param operPerson 操作人
	  * @param operDate 操作时间
	  * @return
	  */
	 public HrDepartmentSnap saveHrDeptSnap(HrDepartmentSnap hrDepartmentSnap,String hrSubSets,Person operPerson,Date operDate,boolean asyncData);
	 /**
	  * 确认新增部门
	  * @param hrDepartmentSnap
	  * @param operPerson
	  * @param operDate
	  */
	 public void confirmSave(HrDepartmentSnap hrDepartmentSnap,Person operPerson, Date operDate,boolean asyncData);
	 
	 public List<HrDepartmentSnap> getDeptListByHisTime(String timestamp);
	 /**
	  * HR往系统部门同步
	  * @param deptSnap
	  * @param operType
	  */
	 public void syncUpdateDepartment(HrDepartmentSnap deptSnap,String operType);
	 /**
	  * 系统部门往HR同步
	  * @param hrOrgSnap
	  * @param deptId
	  * @param snapCode
	  * @param person
	  * @param date
	  * @return
	  */
	 public void syncUpdateHrDepartment(String orgCode,String deptId,String snapCode,Person person,Date date);
	/**
	 * 系统部门往HR同步(系统部门信息界面)
	 * @param deptId
	 */
	 public void syncHrDepartment(Department dept,Person person,Date date);
	 
	 /**
	  * HrDepartmentSnap上锁
	  * @param deptId
	  * @param lockState
	  */
	 public void lockHrDepartmentSnap(String deptId,String lockState);
	 /**
	  * HrDepartmentSnap解锁
	  * @param deptId
	  * @param lockState
	  */
	 public void unlockHrDepartmentSnap(String deptId,String lockState);
	 /**
	  * 取最新部门
	  * @param deptId
	  * @return
	  */
	 public HrDepartmentSnap getMaxHrDepartmentSnap(String deptId);
	 /**
	  * 获取某一时间节点下面的DeptId和dept的map
	  * @param hisTime
	  * @return
	  */
	 public Map<String,HrDepartmentSnap> getDeptIdDeptMap(String hisTime);
	 
	 public List<HrDepartmentSnap> getHrDepartmentSnapListBysql(String sql);
}