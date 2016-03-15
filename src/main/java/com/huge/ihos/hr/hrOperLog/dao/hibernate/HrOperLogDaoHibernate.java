package com.huge.ihos.hr.hrOperLog.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.dao.HrOperLogDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrOperLogDao")
public class HrOperLogDaoHibernate extends GenericDaoHibernate<HrOperLog, String> implements HrOperLogDao {

    public HrOperLogDaoHibernate() {
        super(HrOperLog.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrOperLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrOperLog.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrOperLogCriteria", e);
			return paginatedList;
		}

	}
}
