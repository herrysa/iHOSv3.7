package com.huge.ihos.kq.generalHoliday.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.generalHoliday.model.GeneralHoliday;
import com.huge.ihos.kq.generalHoliday.dao.GeneralHolidayDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("generalHolidayDao")
public class GeneralHolidayDaoHibernate extends GenericDaoHibernate<GeneralHoliday, String> implements GeneralHolidayDao {

    public GeneralHolidayDaoHibernate() {
        super(GeneralHoliday.class);
    }
    
    public JQueryPager getGeneralHolidayCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("holidayId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GeneralHoliday.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGeneralHolidayCriteria", e);
			return paginatedList;
		}

	}
}
