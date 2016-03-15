package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.AllotItemDetail;

public class AllotItemDetailDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private AllotItemDetailDao allotItemDetailDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveAllotItemDetail() {
        AllotItemDetail allotItemDetail = new AllotItemDetail();

        // enter all required fields
        allotItemDetail.setCostRatio( new BigDecimal( 0 ) );

        log.debug( "adding allotItemDetail..." );
        allotItemDetail = allotItemDetailDao.save( allotItemDetail );

        allotItemDetail = allotItemDetailDao.get( allotItemDetail.getAllotItemDetailId() );

        assertNotNull( allotItemDetail.getAllotItemDetailId() );

        log.debug( "removing allotItemDetail..." );

        allotItemDetailDao.remove( allotItemDetail.getAllotItemDetailId() );

        // should throw DataAccessException 
        allotItemDetailDao.get( allotItemDetail.getAllotItemDetailId() );
    }
}