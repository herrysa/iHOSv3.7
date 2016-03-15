package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.AllotSet;

public class AllotSetDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private AllotSetDao allotSetDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveAllotSet() {
        AllotSet allotSet = new AllotSet();

        // enter all required fields
        allotSet.setAllotSetName( "PnBsQaAtPtXtNtLrKlOkPtBwCySrWbYgPwFmNvGiZeBjQbEnCdRkGiNiGnAuCjYxAmDaMoJkKdOuWxMqTrKzVdBfIeNiGsKsUkMp" );
        allotSet.setDisabled( Boolean.FALSE );

        log.debug( "adding allotSet..." );
        allotSet = allotSetDao.save( allotSet );

        allotSet = allotSetDao.get( allotSet.getAllotSetId() );

        assertNotNull( allotSet.getAllotSetId() );

        log.debug( "removing allotSet..." );

        allotSetDao.remove( allotSet.getAllotSetId() );

        // should throw DataAccessException 
        allotSetDao.get( allotSet.getAllotSetId() );
    }
}