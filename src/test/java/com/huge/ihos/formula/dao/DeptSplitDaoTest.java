package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.DeptSplit;

public class DeptSplitDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DeptSplitDao deptSplitDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDeptSplit() {
        DeptSplit deptSplit = new DeptSplit();

        // enter all required fields
        deptSplit.setCostratio( new BigDecimal( 0 ) );
        deptSplit.setDeptid( "QpVoUqUqMlRqYbMcLqJc" );
        deptSplit.setDeptname( "DaKdYuNcVqBlWcXiWoLeHtFcEoWhYhCmGoIwJhQgOoNnPzAiOd" );
        deptSplit.setDisabled( Boolean.FALSE );
        deptSplit.setPubdeptid( "MoDsMmYoHxOgAuVwQiKd" );
        deptSplit.setPubdeptname( "RzXmSdCqArSkGwRpLeVvBhGhClAqDlUrAcIrIpVrBvFvHaWcMv" );

        log.debug( "adding deptSplit..." );
        deptSplit = deptSplitDao.save( deptSplit );

        deptSplit = deptSplitDao.get( deptSplit.getDeptSplitId() );

        assertNotNull( deptSplit.getDeptSplitId() );

        log.debug( "removing deptSplit..." );

        deptSplitDao.remove( deptSplit.getDeptSplitId() );

        // should throw DataAccessException 
        deptSplitDao.get( deptSplit.getDeptSplitId() );
    }
}