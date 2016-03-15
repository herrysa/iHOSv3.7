package com.huge.ihos.system.systemManager.organization.service;

import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonUpCost;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface PersonUpCostManager
    extends GenericManager<PersonUpCost, String> {
    public JQueryPager getPersonCriteria( final JQueryPager paginatedList );
    
    public List<PersonUpCost> getPersonsByDept(Department department);
    
    public List getAllPerson();
    
    public List<PersonUpCost> getPersonsByView(String viewName , Department department);
}