package com.huge.ihos.kq.kqHoliday.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqHoliday.model.KqHoliday;
import com.huge.ihos.kq.kqHoliday.dao.KqHolidayDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqHolidayDao")
public class KqHolidayDaoHibernate extends GenericDaoHibernate<KqHoliday, String> implements KqHolidayDao {

    public KqHolidayDaoHibernate() {
        super(KqHoliday.class);
    }
    
    public JQueryPager getKqHolidayCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("holidayId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqHoliday.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqHolidayCriteria", e);
			return paginatedList;
		}

	}
}
