package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.dao.GenericSPDao;
import com.huge.ihos.formula.service.AllotFacadeManager;
import com.huge.ihos.period.dao.PeriodDao;
import com.huge.util.UUIDGenerator;

@Service( "allotFacadeManager" )
public class AllotFacadeManagerImpl
    implements AllotFacadeManager {
    PeriodDao periodDao;

    GenericSPDao spDao;

    public GenericSPDao getSpDao() {
        return spDao;
    }

    @Autowired
    public void setSpDao( GenericSPDao spDao ) {
        this.spDao = spDao;
    }

    public PeriodDao getPeriodDao() {
        return periodDao;
    }

    @Autowired
    public void setPeriodDao( PeriodDao periodDao ) {
        this.periodDao = periodDao;
    }

    /*public String getCurrentPeriod() {
        return this.periodDao.getCurrentPeriod().getPeriodCode();
    }*/

    public String publicPrecess( String taskName, Object[] args ) {
        return this.spDao.processSp( taskName, args );
    }

    public String getRuntimeInterId() {
        return UUIDGenerator.getInstance().getNextValue16();
    }
}
