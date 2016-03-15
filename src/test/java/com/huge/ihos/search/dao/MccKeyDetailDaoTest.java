package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDetailDao;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;

public class MccKeyDetailDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private MccKeyDetailDao mccKeyDetailDao;

    @Ignore
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveMccKeyDetail() {
        MccKeyDetail mccKeyDetail = new MccKeyDetail();

        // enter all required fields

        log.debug( "adding mccKeyDetail..." );
        mccKeyDetail = mccKeyDetailDao.save( mccKeyDetail );

        mccKeyDetail = mccKeyDetailDao.get( mccKeyDetail.getMccKeyDetailId() );

        assertNotNull( mccKeyDetail.getMccKeyDetailId() );

        log.debug( "removing mccKeyDetail..." );

        mccKeyDetailDao.remove( mccKeyDetail.getMccKeyDetailId() );

        // should throw DataAccessException 
        mccKeyDetailDao.get( mccKeyDetail.getMccKeyDetailId() );
    }
    @Test
    public void testmcckeyDetail() throws SQLException{
    	String [] data={"A1005","select * from t_MccKeyDetail"};
    	System.out.println(mccKeyDetailDao.clockDialMethod(data));
    }
}