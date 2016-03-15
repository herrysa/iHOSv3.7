package com.huge.ihos.period.service;

import java.util.Date;
import java.util.List;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.period.model.Period;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface PeriodManager
    extends GenericManager<Period, Long> {
    public JQueryPager getPeriodCriteria( final JQueryPager paginatedList );

//    public Period getCurrentPeriod()
//        throws BusinessException;
//
//    public Period getCurrentDCPeriod()
//        throws BusinessException;

    //    public void closeCheckPeriod(String periodCode);
    public void closeDataCollectPeriod( String periodCode );

    public boolean isCheckPeriodClosed( String periodCode );

    public boolean isDataCollectPeriodClosed( String periodCode );

    public void setCurrentPeriod( String periodCode );

    public void savePeriod( Period period );

    public void deletePeriod( String periodCode );

    public void openDataCollectionPeriod( String periodCode );
    
    public String getPeriodCodeByDate(Date date);
    
    public List<Period> getLessCurrentPeriod(String currentPeriod);
    
    public List<Period> getPeriodsBetween(String beginPeriod,String endPeriod);

	public Period getPeriodByCode(String periodCode);
	
	public String getNextPeriod(String period);
	
	public List<String> getMonthList(String year);
	
	public List<Period> getPeriodsByYear(String year);
	
	public List<String> getYearList();
    

}