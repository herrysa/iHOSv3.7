package com.huge.ihos.search.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.reportManager.search.dao.ReportDao;
import com.huge.ihos.system.reportManager.search.model.Report;

public class ReportDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private ReportDao reportDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveReport() {
        Report report = new Report();

        // enter all required fields
        report.setGroupName( "YaReRgKzSbLpCyDmNbUfTxXkKsYfYg" );
        report.setReportName( "LnYyAwRjQrAiIrOxIwVkGfGmEdWlTcHnFoHsOgPlYxCgLwCwUf" );

        log.debug( "adding report..." );
        report = reportDao.save( report );

        report = reportDao.get( report.getReportId() );

        assertNotNull( report.getReportId() );

        log.debug( "removing report..." );

        reportDao.remove( report.getReportId() );

        // should throw DataAccessException 
        reportDao.get( report.getReportId() );
    }
}