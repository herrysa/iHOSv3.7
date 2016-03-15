package com.huge.ihos.kq.generalHoliday.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.generalHoliday.dao.GeneralHolidayChangeDao;
import com.huge.ihos.kq.generalHoliday.model.GeneralHolidayChange;
import com.huge.ihos.kq.generalHoliday.service.GeneralHolidayChangeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("generalHolidayChangeManager")
public class GeneralHolidayChangeManagerImpl extends GenericManagerImpl<GeneralHolidayChange, String> implements GeneralHolidayChangeManager {
    private GeneralHolidayChangeDao generalHolidayChangeDao;

    @Autowired
    public GeneralHolidayChangeManagerImpl(GeneralHolidayChangeDao generalHolidayChangeDao) {
        super(generalHolidayChangeDao);
        this.generalHolidayChangeDao = generalHolidayChangeDao;
    }
    
    public JQueryPager getGeneralHolidayChangeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return generalHolidayChangeDao.getGeneralHolidayChangeCriteria(paginatedList,filters);
	}
    @Override
    public Map<String, Boolean> getMonthHoliday(int year,int month,int days){
    	Map<String, Boolean> map = new HashMap<String, Boolean>();
    	String monthStr = "" + month;
    	if(month < 10){
    		monthStr = "0" + month;
    	}
    	String beginDateStr = year + "-" + monthStr + "-" + "01";
    	String endDateStr = year + "-" + monthStr + "-" + days;
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("GED_oldDate",beginDateStr));
    	filters.add(new PropertyFilter("LED_oldDate",endDateStr));
    	List<GeneralHolidayChange> holidayChanges = generalHolidayChangeDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(holidayChanges)&&!holidayChanges.isEmpty()){
    		for(GeneralHolidayChange holidayChange:holidayChanges){
    			Date beginDateTemp = holidayChange.getOldDate();
//    			Date endDateTemp = holidayChange.getNewDate();
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(beginDateTemp);
    			int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
    			map.put("day"+beginDay, false);
    		}
    	}
    	filters.clear();
    	filters.add(new PropertyFilter("GED_newDate",beginDateStr));
    	filters.add(new PropertyFilter("LED_newDate",endDateStr));
    	holidayChanges = generalHolidayChangeDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(holidayChanges)&&!holidayChanges.isEmpty()){
    		for(GeneralHolidayChange holidayChange:holidayChanges){
//    			Date beginDateTemp = holidayChange.getOldDate();
    			Date endDateTemp = holidayChange.getNewDate();
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(endDateTemp);
    			int endDay = calendar.get(Calendar.DAY_OF_MONTH);
    			map.put("day"+endDay, true);
    		}
    	}
    	return map;
    }
}