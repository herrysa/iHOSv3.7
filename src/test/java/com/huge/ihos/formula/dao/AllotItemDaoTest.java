package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.AllotItem;

public class AllotItemDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private AllotItemDao allotItemDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveAllotItem() {
        AllotItem allotItem = new AllotItem();

        // enter all required fields
        allotItem.setAllotCost( new BigDecimal( 0 ) );
        allotItem.setCheckPeriod( "EjBnGa" );

        log.debug( "adding allotItem..." );
        allotItem = allotItemDao.save( allotItem );

        allotItem = allotItemDao.get( allotItem.getAllotItemId() );

        assertNotNull( allotItem.getAllotItemId() );

        log.debug( "removing allotItem..." );

        allotItemDao.remove( allotItem.getAllotItemId() );

        // should throw DataAccessException 
        allotItemDao.get( allotItem.getAllotItemId() );
    }
}