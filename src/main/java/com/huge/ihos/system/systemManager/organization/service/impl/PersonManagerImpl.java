package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.exinterface.ProxySynchronizeToHr;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "personManager" )
public class PersonManagerImpl
    extends GenericManagerImpl<Person, String>
    implements PersonManager {
    PersonDao personDao;

    @Autowired
    public PersonManagerImpl( PersonDao personDao ) {
        super( personDao );
        this.personDao = personDao;
    }
    public JQueryPager getPersonCriteria( JQueryPager paginatedList ) {
        return personDao.getPersonCriteria( paginatedList );
    }

	@Override
	public List<Person> getPersonsByDept(Department department) {
		return personDao.getPersonsByDept(department);
	}

	@Override
	public List getAllPerson() {
		return personDao.getAllPerson();
	}

	@Override
	public List<Person> getPersonsByView(String viewName, Department department) {
		return personDao.getPersonsByView(viewName,department);
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		personDao.disableOrgAfterSync(snapCode);
	}
	@Override
	public Person savePerson(Person person,boolean hrStarted,Person operperson,Date operdate){
		person = personDao.save(person);
		if(hrStarted){
			ProxySynchronizeToHr proxySynchronizeToHr = new ProxySynchronizeToHr();
			proxySynchronizeToHr.snycHrPerson(person, operperson, operdate);
		}
		return person;
	}
}