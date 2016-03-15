package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Person;

public class PersonDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private PersonDao personDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemovePerson() {
        Person person = new Person();

        // enter all required fields
        //        person.setDepartmentId(-1L);
        //        person.setDepartmentName("XjNbUwRjYoHqXgKbRtAuBbCxIsNfIaOyZlSuQvRkOzMuLyZpWk");
        person.setDisable( Boolean.FALSE );
        person.setName( "QpJdDiEbMeRnDuViFlGi" );
        person.setSex( "ZpVvOaPxWg" );

        log.debug( "adding person..." );
        person = personDao.save( person );

        person = personDao.get( person.getPersonId() );

        assertNotNull( person.getPersonId() );

        log.debug( "removing person..." );

        personDao.remove( person.getPersonId() );

        // should throw DataAccessException 
        personDao.get( person.getPersonId() );
    }
}