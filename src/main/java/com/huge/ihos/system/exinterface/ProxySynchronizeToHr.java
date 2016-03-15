package com.huge.ihos.system.exinterface;

import java.util.Date;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.webapp.util.SpringContextHelper;


public class ProxySynchronizeToHr extends SynchronizeToHr{
	SynchronizeToHr realizeSynchronizeToHr = null;
	public ProxySynchronizeToHr(){
		realizeSynchronizeToHr = (SynchronizeToHr)SpringContextHelper.getBean("realizeSynchronizeToHr");
		/*try {
			//Class.forName("com.huge.ihos.hr.exinterface.RealizeSynchronizeToHr").newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void synchronizeOrgToHr() {
		// TODO Auto-generated method stub
		Class<?> demo1=null;
		try {
			Class.forName("");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void jzSynchronizeOrgToHr(String period) {
		if(realizeSynchronizeToHr!=null){
			realizeSynchronizeToHr.jzSynchronizeOrgToHr(period);
		}
	}
	@Override
	public boolean deptInHr(String deptId) {
		if(realizeSynchronizeToHr!=null){
			return realizeSynchronizeToHr.deptInHr(deptId);
		}else{
			return false;
		}
	}
	@Override
	public void syncHrDepartment(Department dept, Person person, Date date) {
		if(realizeSynchronizeToHr!=null){
			realizeSynchronizeToHr.syncHrDepartment(dept, person, date);
		}
	}
	@Override
	public void snycHrPerson(Person person, Person operperson, Date operdate) {
		if(realizeSynchronizeToHr!=null){
			realizeSynchronizeToHr.snycHrPerson(person, operperson, operdate);
		}		
	}
}
