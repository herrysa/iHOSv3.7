package com.huge.ihos.system.systemManager.organization.service;

import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonJj;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface PersonJjManager
    extends GenericManager<PersonJj, String> {
    public JQueryPager getPersonCriteria( final JQueryPager paginatedList );
    
    public List<PersonJj> getPersonsByDept(Department department);
    
    public List getAllPerson();
    
    public List<PersonJj> getPersonsByView(String viewName , Department department);
}