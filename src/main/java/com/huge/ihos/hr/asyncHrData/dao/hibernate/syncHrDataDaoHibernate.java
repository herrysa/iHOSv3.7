package com.huge.ihos.hr.asyncHrData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.ihos.hr.asyncHrData.dao.syncHrDataDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("syncHrDataDao")
public class syncHrDataDaoHibernate extends GenericDaoHibernate<syncHrData, String> implements syncHrDataDao {

    public syncHrDataDaoHibernate() {
        super(syncHrData.class);
    }
    
    public JQueryPager getsyncHrDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("syncHrId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, syncHrData.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getsyncHrDataCriteria", e);
			return paginatedList;
		}

	}
}
