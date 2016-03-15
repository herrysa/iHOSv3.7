package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.dao.PersonJjDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonJj;
import com.huge.ihos.system.systemManager.organization.service.PersonJjManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "personJjManager" )
public class PersonJjManagerImpl
    extends GenericManagerImpl<PersonJj, String>
    implements PersonJjManager {
	PersonJjDao personDao;

    @Autowired
    public PersonJjManagerImpl( PersonJjDao personDao ) {
        super( personDao );
        this.personDao = personDao;
    }

    public JQueryPager getPersonCriteria( JQueryPager paginatedList ) {
        return personDao.getPersonCriteria( paginatedList );
    }

	@Override
	public List<PersonJj> getPersonsByDept(Department department) {
		return personDao.getPersonsByDept(department);
	}

	@Override
	public List getAllPerson() {
		return personDao.getAllPerson();
	}

	@Override
	public List<PersonJj> getPersonsByView(String viewName, Department department) {
		return personDao.getPersonsByView(viewName,department);
	}

}