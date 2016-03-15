package com.huge.ihos.period.dao;

import java.util.Date;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.period.model.Period;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Period table.
 */
public interface PeriodDao
    extends GenericDao<Period, Long> {

    public JQueryPager getPeriodCriteria( final JQueryPager paginatedList );

    //public Period getCurrentPeriod();

    //public Period getCurrentDCPeriod();

    public void closeCheckPeriod( String periodCode );

    public void closeDataCollectPeriod( String periodCode );

    public boolean isCheckPeriodClosed( String periodCode );

    public boolean isDataCollectPeriodClosed( String periodCode );

    public void setCurrentPeriod( String periodCode );

    // public void savePeriod(Period period);
    public void deletePeriod( String periodCode );

    public Period getPeriodByCode( String periodCode );

    public List getAllListMapOfPeriodBySql();

    public Object getPageOfPeriodBySql();
    
    public String getPeriodCodeByDate(Date date);
    
    public List<Period> getPeriodsBetween(String beginPeriod,String endPeriod);

	public List<Period> getLessCurrentPeriod(String currentPeriod);
	
	public List<Period> getPeriodsByYear(String year);
	
	public List<String> getYearList();
}
