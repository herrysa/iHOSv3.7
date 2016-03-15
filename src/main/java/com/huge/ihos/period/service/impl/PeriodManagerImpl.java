package com.huge.ihos.period.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "periodManager" )
public class PeriodManagerImpl
    extends GenericManagerImpl<Period, Long>
    implements PeriodManager {
    PeriodDao periodDao;

    DataCollectionTaskManager dataCollectionTaskManager;

    public DataCollectionTaskManager getDataCollectionTaskManager() {
        return dataCollectionTaskManager;
    }

    @Autowired
    public void setDataCollectionTaskManager( DataCollectionTaskManager dataCollectionTaskManager ) {
        this.dataCollectionTaskManager = dataCollectionTaskManager;
    }

    @Autowired
    public PeriodManagerImpl( PeriodDao periodDao ) {
        super( periodDao );
        this.periodDao = periodDao;
    }

    public JQueryPager getPeriodCriteria( JQueryPager paginatedList ) {
        return periodDao.getPeriodCriteria( paginatedList );
    }

   /* public Period getCurrentPeriod()
        throws BusinessException {
        return this.periodDao.getCurrentPeriod();
    }*/

    // TODO 需要根据实际业务进行修订
    private boolean isCheckPeriodRelateBusinessCompleted( String periodCode ) {
        // TODO 需要根据实际业务进行修订
        return true;
    }

    /**
     * 
     */
    public void closeDataCollectPeriod( String periodCode ) {
        if ( !dataCollectionTaskManager.isAllowColsePeriod( periodCode ) ) {
        	throw new BusinessException( "相关的业务尚没有完成，不能关闭此数据采集期间" );
        }
        else {

            Period period = this.periodDao.getPeriodByCode( periodCode );
            period.setCdpFlag( false );
            this.periodDao.save( period );
        }
    }

    // TODO 需要根据实际业务进行修订
    private boolean isDataCollectionPeriodRelateBusinessCompleted( String periodCode ) {
        Period period = this.periodDao.getPeriodByCode( periodCode );
        int count = this.dataCollectionTaskManager.getPeriodRemainTaskNum( periodCode );
        if ( count > 0 )
            return false;
        else
            return true;
    }

    public boolean isCheckPeriodClosed( String periodCode ) {
        return !periodDao.getPeriodByCode( periodCode ).getCpFlag();
    }

    public boolean isDataCollectPeriodClosed( String periodCode ) {
        return !periodDao.getPeriodByCode( periodCode ).getCdpFlag();
    }

    // 
    // /**
    // * 1、需要校验当前的数据采集期间是否已经关闭
    // * 2、与核算期间依赖的业务是否都已经完成
    // */
    // public void closeCheckPeriod(String periodCode) {
    // Period period = this.periodDao.getPeriodByCode(periodCode);
    // if (period.getCdpFlag() == true) {
    // throw new BusinessException(
    // "data collection period has not been closed,can not close this check period.");
    // } else if (!isCheckPeriodRelateBusinessCompleted(periodCode)) {
    // throw new BusinessException(
    // "relate business has not completed yet,can not close this check period.");
    // } else {
    // period.setCpFlag(false);
    // this.periodDao.save(period);
    // }
    // }

    public void setCurrentPeriod( String periodCode ) {
        // 先关闭原有的当前期间
        //Period period = this.getCurrentPeriod();
        Period period = null;

        if ( period.getCdpFlag() == true ) {
            throw new BusinessException( "数据采集期间尚未关闭，不能关闭此核算期间。" );
        }
        else if ( !isCheckPeriodRelateBusinessCompleted( periodCode ) ) {
            throw new BusinessException( "相关的业务尚没有完成，不能关闭此数据采集期间。" );
        }
        else {
            period.setCpFlag( false );
            this.periodDao.save( period );
            // 再打开指定的期间
            period = this.periodDao.getPeriodByCode( periodCode );
            period.setCpFlag( true );
            this.periodDao.save( period );
        }
    }

    public void savePeriod( Period period ) {
        if ( this.isPeriodUsed( period.getPeriodCode() ) ) {
            // TODO 进行规则性验证
            throw new BusinessException( "此期间正在被使用，不能被修改。" );
        }
        else {
            this.periodDao.save( period );
        }
    }

    public void deletePeriod( String periodCode ) {
        if ( this.isPeriodUsed( periodCode ) ) {
            throw new BusinessException( "此期间正在被使用，不能删除。" );
        }
        else {
            Period period = this.periodDao.getPeriodByCode( periodCode );
            this.periodDao.remove( period.getPeriodId() );
        }

    }

    public void openDataCollectionPeriod( String periodCode ) {
        //Period period = this.getCurrentPeriod();
        Period period = this.periodDao.getPeriodByCode( periodCode );
        period.setCdpFlag( true );
        this.periodDao.save( period );
    }

    private boolean isPeriodUsed( String periodCode ) {
        // TODO 检查是否此期间已经被使用，如果被使用了则不能删除、code不能被修改
        return false;
    }

//    @Override
//    public Period getCurrentDCPeriod()
//        throws BusinessException {
//        return this.periodDao.getCurrentDCPeriod();
//    }

	@Override
	public String getPeriodCodeByDate(Date date) {
		return this.periodDao.getPeriodCodeByDate(date);
	}

	@Override
	public List<Period> getLessCurrentPeriod(String currentPeriod) {
		return this.periodDao.getLessCurrentPeriod(currentPeriod);
	}

    @Override
    public List<Period> getPeriodsBetween( String beginPeriod, String endPeriod ) {
        return this.periodDao.getPeriodsBetween( beginPeriod, endPeriod);
    }

	@Override
	public Period getPeriodByCode(String periodCode) {
		return this.periodDao.getPeriodByCode(periodCode);
	}

	@Override
	public String getNextPeriod(String periodCode) {
		// 得到本年所有期间(有序)
		String nextPeriod = null;
		String year = periodCode.substring(0, 4);
		List<Period> periods = periodDao.getPeriodsByYear(year);
		for(int i=0,len = periods.size();i<len;i++){
			if(periods.get(i).getPeriodCode().equals(periodCode)){
				if(i<len-1){
					nextPeriod =  periods.get(i+1).getPeriodCode();
				}else{
					nextPeriod = null;
				}
				break;
			}
		}
		return nextPeriod;
	}

	@Override
	public List<String> getMonthList(String year) {
		List<Period> periods = this.getPeriodsByYear(year);
		List<String> monthList = null;
		if(periods!=null && periods.size()>0){
			monthList = new ArrayList<String>();
			for(Period period:periods){
				monthList.add(period.getPeriodCode());
			}
		}
		return monthList;
	}

	@Override
	public List<Period> getPeriodsByYear(String year) {
		return this.periodDao.getPeriodsByYear(year);
	}

	@Override
	public List<String> getYearList() {
		return this.periodDao.getYearList();
	}
}