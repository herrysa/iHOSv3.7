package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.AllotPrinciple;

public class AllotPrincipleDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private AllotPrincipleDao allotPrincipleDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveAllotPrinciple() {
        AllotPrinciple allotPrinciple = new AllotPrinciple();

        // enter all required fields
        allotPrinciple.setAllotPrincipleName( "FzXyQkIqEyIlUqVzCbKsZeHsOsSeRlXsUwMnShIeGlOaAuPfVpBmYdYkUxDlMoNiVsGiHqWbIiZzNdYmFvRvYhJaVxEmSvSyOkGh" );
        allotPrinciple.setDisabled( Boolean.FALSE );
        allotPrinciple.setParam1( "KwYfJwDuMdJfKoMkNnTuJhBzFbSmAyGrFcTsNxOjIfQaCsZvVqDkEpItZyNnPkJiQySrOrDhXpMsDgPcInMaRgDeJgQlLbYiXqJb" );
        allotPrinciple.setParamed( Boolean.FALSE );

        log.debug( "adding allotPrinciple..." );
        allotPrinciple = allotPrincipleDao.save( allotPrinciple );

        allotPrinciple = allotPrincipleDao.get( allotPrinciple.getAllotPrincipleId() );

        assertNotNull( allotPrinciple.getAllotPrincipleId() );

        log.debug( "removing allotPrinciple..." );

        allotPrincipleDao.remove( allotPrinciple.getAllotPrincipleId() );

        // should throw DataAccessException 
        allotPrincipleDao.get( allotPrinciple.getAllotPrincipleId() );
    }
}