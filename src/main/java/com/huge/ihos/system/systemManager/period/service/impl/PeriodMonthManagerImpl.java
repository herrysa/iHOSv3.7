package com.huge.ihos.system.systemManager.period.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.period.dao.PeriodMonthDao;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("periodMonthManager")
public class PeriodMonthManagerImpl extends GenericManagerImpl<PeriodMonth, String> implements PeriodMonthManager {
    private PeriodMonthDao periodMonthDao;

    @Autowired
    public PeriodMonthManagerImpl(PeriodMonthDao periodMonthDao) {
        super(periodMonthDao);
        this.periodMonthDao = periodMonthDao;
    }
    
    public JQueryPager getPeriodMonthCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return periodMonthDao.getPeriodMonthCriteria(paginatedList,filters);
	}

	@Override
	public List<PeriodMonth> getMonthByPlanAndYear(String planId, String kjYear) {
		return periodMonthDao.getMonthByPlanAndYear(planId, kjYear);
	}

	@Override
	public PeriodMonth getPeriodMonth(String planId,Date optDate) {
		return periodMonthDao.getPeriodMonth(planId, optDate);
	}


	@Override
	public String getMonthsBetweenPeriods(String beginPeriod, String endPeriod,
			String planId) {
		List<PeriodMonth> monthList =  getMonthListBetweenPeriods(beginPeriod, endPeriod, planId);
		StringBuffer returnStr = new StringBuffer();
		for(PeriodMonth periodMonth : monthList){
			returnStr.append(periodMonth.getMonth());
			returnStr.append(",");
		}
		if(returnStr.length() > 1){
			returnStr.deleteCharAt(returnStr.length()-1);
		}
		return returnStr.toString();
	}

	@Override
	public List<PeriodMonth> getMonthListBetweenPeriods(String beginPeriod,
			String endPeriod, String planId) {
		return periodMonthDao.getMonthListBetweenPeriods(beginPeriod,endPeriod,planId);
	}

	@Override
	public List<PeriodMonth> getLessCurrentPeriod(String currentPeriod) {
		return periodMonthDao.getLessCurrentPeriod(currentPeriod);
	}

	/*@Override
	public List<PeriodMonth> getPeriodsBetween(String beginPeriod,
			String endPeriod) {
		return periodMonthDao.getPeriodsBetween(beginPeriod, endPeriod);
	}*/

	@Override
	public PeriodMonth getPeriodByCode(String periodCode) {
		return periodMonthDao.getPeriodByCode(periodCode);
	}
	@Override
	public String getNextPeriod(String planId, String kjYear,String currentPeriod) {
		List<PeriodMonth> periodMonths = periodMonthDao.getMonthByPlanAndYear(planId, kjYear);
		int count = -1;
		for(int i=0;i<periodMonths.size();i++){
			if(periodMonths.get(i).getPeriodMonthCode().equals(currentPeriod)){
				count = i + 1;
				break;
			}
		}
		if(count==-1 || count>=periodMonths.size()){
			return null;
		}else{
			return periodMonths.get(count).getPeriodMonthCode();
		}
	}
}