package com.huge.ihos.system.systemManager.organization.service;

import java.util.Date;
import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface PersonManager
    extends GenericManager<Person, String> {
    public JQueryPager getPersonCriteria( final JQueryPager paginatedList );
    
    public List<Person> getPersonsByDept(Department department);
    
    public List getAllPerson();
    
    public List<Person> getPersonsByView(String viewName , Department department);
	/**
     * 同步单位后停用未同步到的单位
     * @param snapCode
     */
    public void disableOrgAfterSync(String snapCode);
    public Person savePerson(Person person,boolean hrStarted,Person operperson,Date operdate);
} 