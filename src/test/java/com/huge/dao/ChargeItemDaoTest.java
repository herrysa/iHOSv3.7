package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.model.ChargeItem;

public class ChargeItemDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private ChargeItemDao chargeItemDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveChargeItem() {
        ChargeItem chargeItem = new ChargeItem();

        // enter all required fields
        chargeItem.setChargeItemId( "NgTdSlRlWqNiBeZbNmHm" );
        chargeItem.setDisabled( Boolean.FALSE );

        log.debug( "adding chargeItem..." );
        chargeItem = chargeItemDao.save( chargeItem );

        chargeItem = chargeItemDao.get( chargeItem.getChargeItemId());

        assertNotNull( chargeItem.getChargeItemNo() );

        log.debug( "removing chargeItem..." );

        chargeItemDao.remove( chargeItem.getChargeItemId() );

        // should throw DataAccessException 
        chargeItemDao.get( chargeItem.getChargeItemId());
    }
}