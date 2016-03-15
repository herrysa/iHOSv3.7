package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.dao.PersonUpCostDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonUpCost;
import com.huge.ihos.system.systemManager.organization.service.PersonUpCostManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "personUpCostManager" )
public class PersonUpCostManagerImpl
    extends GenericManagerImpl<PersonUpCost, String>
    implements PersonUpCostManager {
    PersonUpCostDao personDao;

    @Autowired
    public PersonUpCostManagerImpl( PersonUpCostDao personDao ) {
        super( personDao );
        this.personDao = personDao;
    }

    public JQueryPager getPersonCriteria( JQueryPager paginatedList ) {
        return personDao.getPersonCriteria( paginatedList );
    }

	@Override
	public List<PersonUpCost> getPersonsByDept(Department department) {
		return personDao.getPersonsByDept(department);
	}

	@Override
	public List getAllPerson() {
		return personDao.getAllPerson();
	}

	@Override
	public List<PersonUpCost> getPersonsByView(String viewName, Department department) {
		return personDao.getPersonsByView(viewName,department);
	}

}