package com.huge.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.period.model.Period;
import com.huge.webapp.util.SpringContextHelper;

public class PeriodDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private PeriodDao periodDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemovePeriod() {
        Period period = new Period();

        // enter all required fields
        period.setCdpFlag( Boolean.FALSE );
        period.setCmonth( "Zt" );
        period.setCpFlag( Boolean.FALSE );
        period.setCyear( "JbPe" );
        period.setEndDate( new java.util.Date() );
        period.setPeriodCode( "BvMmWk" );
        period.setStartDate( new java.util.Date() );

        log.debug( "adding period..." );
        period = periodDao.save( period );

        period = periodDao.get( period.getPeriodId() );

        assertNotNull( period.getPeriodId() );

        log.debug( "removing period..." );

        periodDao.remove( period.getPeriodId() );

        // should throw DataAccessException 
        periodDao.get( period.getPeriodId() );
    }

   // @Test
    public void testListMap() {
        List list = periodDao.getAllListMapOfPeriodBySql();
        assertNotNull( list );
        assertTrue( list.size() > 0 );

        Iterator itr = list.iterator();
        while ( itr.hasNext() ) {
            Map ent = (Map) itr.next();

            Iterator itrm = ent.keySet().iterator();
            while ( itrm.hasNext() ) {

                Object key = itrm.next();
                System.out.print( key + ":" + ent.get( key ) + " " );
            }
            System.out.println();
        }

    }

    @Test
    public void testCallBack() {
        Object obj = this.periodDao.getPageOfPeriodBySql();
        assertNotNull( obj );
    }

    @Test
    public void testJdbcTemplate() {
        JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
        //List list = jt.queryForList("select * from t_period");
        //Map map = jt.queryForMap("select * from t_period");
        SqlRowSet rs = jt.queryForRowSet( "select periodId,periodCode from t_period order by periodCode asc" );
        ArrayList l1 = new ArrayList();
        ArrayList l2 = new ArrayList();
        while ( rs.next() ) {

            l1.add( rs.getString( 1 ) );
            l2.add( rs.getString( 2 ) );
        }

        String[][] arr = new String[l2.size()][2];
        for ( int i = 0; i < l1.size(); i++ ) {
            arr[i][0] = (String) l1.get( i );
            arr[i][1] = (String) l2.get( i );
        }
        assertNotNull( rs );
    }
    
    
   // @Test
    public void testBw(){
        List l = this.periodDao.getPeriodsBetween( "201111", "201209" );
        
        assertTrue( l.size()==9 );
    }
}