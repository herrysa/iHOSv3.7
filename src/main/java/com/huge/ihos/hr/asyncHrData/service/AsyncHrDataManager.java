package com.huge.ihos.hr.asyncHrData.service;

import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;

public interface AsyncHrDataManager {
	/**
	 * 同步单位部门人员数据
	 * @param timestamp 时间戳
	 */
	public void asyncHrData(String timestamp);
	
	public void asyncToHrData(List<String> idl,String orgCode,String orgName,Boolean isHrOrg,String deptIds,Person person,Date date);
	public void asyncToHr(List<HrDepartmentSnap> hdss,List<Department> depts,List<HrLogEntityInfo<HrDepartmentSnap>> entityDeptInfos,List<HrPersonSnap> hpss,List<Person> personList,List<HrLogEntityInfo<HrPersonSnap>> entityPersonInfos,Person operPerson,Date operDate);
}
