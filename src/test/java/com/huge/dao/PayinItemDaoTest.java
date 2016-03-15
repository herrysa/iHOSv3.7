package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.PayinItemDao;
import com.huge.ihos.inout.model.PayinItem;

public class PayinItemDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private PayinItemDao payinItemDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemovePayinItem() {
        PayinItem payinItem = new PayinItem();

        // enter all required fields
        payinItem.setDisabled( Boolean.FALSE );
        payinItem.setPayinItemName( "BwInWkWcVzPwWlEeRxNtUeWwZbUjLg" );

        log.debug( "adding payinItem..." );
        payinItem = payinItemDao.save( payinItem );

        payinItem = payinItemDao.get( payinItem.getPayinItemId() );

        assertNotNull( payinItem.getPayinItemId() );

        log.debug( "removing payinItem..." );

        payinItemDao.remove( payinItem.getPayinItemId() );

        // should throw DataAccessException 
        payinItemDao.get( payinItem.getPayinItemId() );
    }
}