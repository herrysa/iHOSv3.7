package com.huge.ihos.kq.kqHoliday.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.kqHoliday.dao.KqHolidayDao;
import com.huge.ihos.kq.kqHoliday.model.KqHoliday;
import com.huge.ihos.kq.kqHoliday.service.KqHolidayManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqHolidayManager")
public class KqHolidayManagerImpl extends GenericManagerImpl<KqHoliday, String> implements KqHolidayManager {
    private KqHolidayDao kqHolidayDao;

    @Autowired
    public KqHolidayManagerImpl(KqHolidayDao kqHolidayDao) {
        super(kqHolidayDao);
        this.kqHolidayDao = kqHolidayDao;
    }
    
    public JQueryPager getKqHolidayCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqHolidayDao.getKqHolidayCriteria(paginatedList,filters);
	}
    @Override
    public Map<String, Boolean> getMonthHoliday(int year,int month,int days){
    	Map<String, Boolean> map = new HashMap<String, Boolean>();
    	try {
    		String monthStr = "" + month;
        	if(month < 10){
        		monthStr = "0" + month;
        	}
        	String beginDateStr = year + "-" + monthStr + "-"  + "01";
        	String endDateStr = year + "-" + monthStr + "-" + days;
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        	filters.add(new PropertyFilter("GED_beginDate",beginDateStr));
        	filters.add(new PropertyFilter("LED_beginDate",endDateStr));
        	List<KqHoliday> kqHolidays = kqHolidayDao.getByFilters(filters);
        	if(OtherUtil.measureNotNull(kqHolidays)&&!kqHolidays.isEmpty()){
        		for(KqHoliday kqHoliday:kqHolidays){
        			Date beginDateTemp = kqHoliday.getBeginDate();
        			Date endDateTemp = kqHoliday.getEndDate();
        			Calendar calendar = Calendar.getInstance();
        			calendar.setTime(beginDateTemp);
        			int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
        			calendar.setTime(endDateTemp);
        			int endYear = calendar.get(Calendar.YEAR);
        			int endMonth = calendar.get(Calendar.MONTH) + 1;
        			int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        			int calType = 0;//0不计算；1本月所有剩余天；2开始到结束
        			if(endYear > year){
        				calType = 1;
        			}else if(endYear == year){
        				if(endMonth > month){
        					calType = 1;
        				}else if(endMonth == month){
        					if(endDay >= beginDay){
        						calType = 2;
        					}
        				}
        			}
        			if(calType == 1){
        				for(int changeIndex = beginDay;changeIndex <= days;changeIndex ++){
        					map.put("day" + changeIndex, true);
        				}
        			}else if(calType == 2){
        				for(int changeIndex = beginDay;changeIndex <= endDay;changeIndex ++){
        					map.put("day" + changeIndex, true);
        				}
        			}
        		}
        	}
        	filters.clear();
        	filters.add(new PropertyFilter("GED_endDate",beginDateStr));
        	filters.add(new PropertyFilter("LED_endDate",endDateStr));
        	kqHolidays = kqHolidayDao.getByFilters(filters);
        	if(OtherUtil.measureNotNull(kqHolidays)&&!kqHolidays.isEmpty()){
        		for(KqHoliday kqHoliday:kqHolidays){
        			Date beginDateTemp = kqHoliday.getBeginDate();
        			Date endDateTemp = kqHoliday.getEndDate();
        			Calendar calendar = Calendar.getInstance();
        			calendar.setTime(endDateTemp);
        			int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        			calendar.setTime(beginDateTemp);
        			int beginYear = calendar.get(Calendar.YEAR);
        			int beginMonth = calendar.get(Calendar.MONTH) + 1;
        			int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
        			int calType = 0;//0不计算；1本月所有剩余天；2开始到结束
        			if(beginYear < year){
        				calType = 1;
        			}else if(beginYear == year){
        				if(beginMonth < month){
        					calType = 1;
        				}else if(beginMonth == month){
        					if(endDay >= beginDay){
        						calType = 2;
        					}
        				}
        			}
        			if(calType == 1){
        				for(int changeIndex = 1;changeIndex <= endDay;changeIndex ++){
        					map.put("day" + changeIndex, true);
        				}
        			}else if(calType == 2){
        				for(int changeIndex = beginDay;changeIndex <= endDay;changeIndex ++){
        					map.put("day" + changeIndex, true);
        				}
        			}
        		}
        	}
		} catch (Exception e) {
			log.error("getMonthHolidayError:"+e.getMessage());
		}
    	return map;
    }
}