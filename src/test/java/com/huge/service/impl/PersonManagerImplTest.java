package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.impl.PersonManagerImpl;

public class PersonManagerImplTest
    extends BaseManagerMockTestCase {
    private PersonManagerImpl manager = null;

    private PersonDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( PersonDao.class );
        manager = new PersonManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetPerson() {
        log.debug( "testing get..." );

        final String personId = "7L";
        final Person person = new Person();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( personId ) ) );
                will( returnValue( person ) );
            }
        } );

        Person result = manager.get( personId );
        assertSame( person, result );
    }

    @Test
    public void testGetPersons() {
        log.debug( "testing getAll..." );

        final List persons = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( persons ) );
            }
        } );

        List result = manager.getAll();
        assertSame( persons, result );
    }

    @Test
    public void testSavePerson() {
        log.debug( "testing save..." );

        final Person person = new Person();
        // enter all required fields
        //        person.setDepartmentId(-1L);
        //        person.setDepartmentName("IpTdSpWuEzNmLdHaBkQuTzTbGlLgUtZjKbFiRaZoYkBfDsDcPk");
        person.setDisable( Boolean.FALSE );
        person.setName( "NtVoKzNsExYoVeTcLzMw" );
        person.setSex( "BcKfXxTnNj" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( person ) ) );
            }
        } );

        manager.save( person );
    }

    @Test
    public void testRemovePerson() {
        log.debug( "testing remove..." );

        final String personId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( personId ) ) );
            }
        } );

        manager.remove( personId );
    }
}