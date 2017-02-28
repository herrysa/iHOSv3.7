package com.huge.ihos.material.order.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.order.model.ImportOrderLog;
import com.huge.ihos.material.order.dao.ImportOrderLogDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("importOrderLogDao")
public class ImportOrderLogDaoHibernate extends GenericDaoHibernate<ImportOrderLog, String> implements ImportOrderLogDao {

    public ImportOrderLogDaoHibernate() {
        super(ImportOrderLog.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getImportOrderLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ImportOrderLog.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getImportOrderLogCriteria", e);
			return paginatedList;
		}

	}
}
