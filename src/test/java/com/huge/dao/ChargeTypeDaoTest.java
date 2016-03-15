package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.model.ChargeType;

public class ChargeTypeDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private ChargeTypeDao chargeTypeDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveChargeType() {
        ChargeType chargeType = new ChargeType();

        // enter all required fields
        chargeType.setChargeTypeName( "GmIlLaHhFdJcCfWcPpLqJoKaUyCmTy" );
        chargeType.setClevel( 4L );
        chargeType.setDisabled( Boolean.FALSE );
        chargeType.setLeaf( Boolean.FALSE );

        log.debug( "adding chargeType..." );
        chargeType = chargeTypeDao.save( chargeType );

        chargeType = chargeTypeDao.get( chargeType.getChargeTypeId() );

        assertNotNull( chargeType.getChargeTypeId() );

        log.debug( "removing chargeType..." );

        chargeTypeDao.remove( chargeType.getChargeTypeId() );

        // should throw DataAccessException 
        chargeTypeDao.get( chargeType.getChargeTypeId() );
    }
}