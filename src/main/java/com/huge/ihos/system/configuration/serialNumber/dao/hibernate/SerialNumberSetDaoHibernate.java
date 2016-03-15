package com.huge.ihos.system.configuration.serialNumber.dao.hibernate;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberSetDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("serialNumberSetDao")
public class SerialNumberSetDaoHibernate extends GenericDaoHibernate<SerialNumberSet, String> implements SerialNumberSetDao {

    public SerialNumberSetDaoHibernate() {
        super(SerialNumberSet.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getSerialNumberSetCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SerialNumberSet.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSerialNumberSetCriteria", e);
			return paginatedList;
		}
	}
}
