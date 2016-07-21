package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.ihos.bm.budgetModel.dao.BmModelProcessLogDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmModelProcessLogDao")
public class BmModelProcessLogDaoHibernate extends GenericDaoHibernate<BmModelProcessLog, String> implements BmModelProcessLogDao {

    public BmModelProcessLogDaoHibernate() {
        super(BmModelProcessLog.class);
    }
    
    public JQueryPager getBmModelProcessLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmModelProcessLog.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmModelProcessLogCriteria", e);
			return paginatedList;
		}

	}
}
