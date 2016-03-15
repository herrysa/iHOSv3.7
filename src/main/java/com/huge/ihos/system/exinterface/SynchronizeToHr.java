package com.huge.ihos.system.exinterface;

import java.util.Date;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;


public abstract class SynchronizeToHr extends SystemCallback{
	
	public abstract void synchronizeOrgToHr();
	
	public abstract void jzSynchronizeOrgToHr(String period);
	
	public abstract boolean deptInHr(String deptId);
	
	public abstract void syncHrDepartment(Department dept,Person person,Date date);
	
	public abstract void snycHrPerson(Person person,Person operperson,Date operdate);
}
