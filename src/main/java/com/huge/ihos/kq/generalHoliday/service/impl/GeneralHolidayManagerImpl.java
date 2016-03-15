package com.huge.ihos.kq.generalHoliday.service.impl;

import java.util.List;
import com.huge.ihos.kq.generalHoliday.dao.GeneralHolidayDao;
import com.huge.ihos.kq.generalHoliday.model.GeneralHoliday;
import com.huge.ihos.kq.generalHoliday.service.GeneralHolidayManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("generalHolidayManager")
public class GeneralHolidayManagerImpl extends GenericManagerImpl<GeneralHoliday, String> implements GeneralHolidayManager {
    private GeneralHolidayDao generalHolidayDao;

    @Autowired
    public GeneralHolidayManagerImpl(GeneralHolidayDao generalHolidayDao) {
        super(generalHolidayDao);
        this.generalHolidayDao = generalHolidayDao;
    }
    
    public JQueryPager getGeneralHolidayCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return generalHolidayDao.getGeneralHolidayCriteria(paginatedList,filters);
	}
}