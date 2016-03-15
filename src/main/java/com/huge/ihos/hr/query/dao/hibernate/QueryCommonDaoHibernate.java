package com.huge.ihos.hr.query.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.ihos.hr.query.dao.QueryCommonDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("queryCommonDao")
public class QueryCommonDaoHibernate extends GenericDaoHibernate<QueryCommon, String> implements QueryCommonDao {

    public QueryCommonDaoHibernate() {
        super(QueryCommon.class);
    }
    
    public JQueryPager getQueryCommonCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, QueryCommon.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getQueryCommonCriteria", e);
			return paginatedList;
		}

	}
}
