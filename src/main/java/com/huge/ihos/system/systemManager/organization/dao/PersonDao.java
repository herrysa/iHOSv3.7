package com.huge.ihos.system.systemManager.organization.dao;

import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Person table.
 */
public interface PersonDao
    extends GenericDao<Person, String> {

    public JQueryPager getPersonCriteria( final JQueryPager paginatedList );

    public List getAllByFilter();

    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );
    
    public List<Person> getPersonsByDept(Department department);
    
    public List getAllPerson();
    
    public List<Person> getPersonsByView(String viewName , Department department);
	/**
     * 同步单位后停用未同步到的单位
     * @param snapCode
     */
    public void disableOrgAfterSync(String snapCode);
}