package com.huge.ihos.system.systemManager.period.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.menu.dao.MenuDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.period.dao.PeriodMonthDao;
import com.huge.ihos.system.systemManager.period.dao.PeriodYearDao;
import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.ihos.system.systemManager.period.service.MoudlePeriodManager;
import com.huge.ihos.system.systemManager.period.service.PeriodYearManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("periodYearManager")
public class PeriodYearManagerImpl extends GenericManagerImpl<PeriodYear, String> implements PeriodYearManager {
    private PeriodYearDao periodYearDao;
    @Autowired
    private PeriodMonthDao periodMonthDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MoudlePeriodManager moudlePeriodDao;

    @Autowired
    public PeriodYearManagerImpl(PeriodYearDao periodYearDao) {
        super(periodYearDao);
        this.periodYearDao = periodYearDao;
    }
    
    public JQueryPager getPeriodYearCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return periodYearDao.getPeriodYearCriteria(paginatedList,filters);
	}

    
   
	public PeriodMonthDao getPeriodMonthDao() {
		return periodMonthDao;
	}

	public void setPeriodMonthDao(PeriodMonthDao periodMonthDao) {
		this.periodMonthDao = periodMonthDao;
	}

	@Override
	public PeriodYear getLastYearByPlan(String planId) {
		return periodYearDao.getLastYearByPlan(planId);
	}

	@Override
	public PeriodYear savePeriodYear(PeriodYear periodYear,String periodSubject_status) {
		try {
			if("edit".equals(periodSubject_status)){
				periodMonthDao.removeByPeriodId(periodYear.getPeriodYearId());
				moudlePeriodDao.removeBySubId(periodYear.getPeriodYearId());
			}
			periodYear = periodYearDao.save(periodYear);
			Set<PeriodMonth> monthSet = periodYear.getPeriodMonthSet();
			List<Menu> menuList = menuDao.getAllRootMenu();
			for(PeriodMonth month: monthSet){
				for(Menu menu:menuList){
					MoudlePeriod modelPeriod = new MoudlePeriod();
					modelPeriod.setMonth(month.getPeriodMonthCode());
					modelPeriod.setPeriodId(periodYear.getPeriodYearId());
					modelPeriod.setPlanId(periodYear.getPlan().getPlanId());
					modelPeriod.setMenuId(menu.getMenuId());
					moudlePeriodDao.save(modelPeriod);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return periodYear;
	}

	@Override
	public PeriodYear getPeriodYearByPlanAndYear(String planId, String year) {
		return periodYearDao.getPeriodYearByPlanAndYear(planId, year);
	}

	@Override
	public PeriodYear getPeriodYearByPlanAndDate(String planId, Date date) {
		return periodYearDao.getPeriodYearByPlanAndDate(planId, date);
	}
}