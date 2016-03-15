package com.huge.ihos.system.systemManager.organization.dao;

import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonJj;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Person table.
 */
public interface PersonJjDao
    extends GenericDao<PersonJj, String> {

    public JQueryPager getPersonCriteria( final JQueryPager paginatedList );

    public List getAllByFilter();

    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );
    
    public List<PersonJj> getPersonsByDept(Department department);
    
    public List getAllPerson();
    
    public List<PersonJj> getPersonsByView(String viewName , Department department);
}