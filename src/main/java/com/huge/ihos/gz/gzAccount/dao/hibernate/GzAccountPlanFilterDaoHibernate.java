package com.huge.ihos.gz.gzAccount.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanFilterDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzAccountPlanFliterDao")
public class GzAccountPlanFilterDaoHibernate extends GenericDaoHibernate<GzAccountPlanFilter, String> implements GzAccountPlanFilterDao {

    public GzAccountPlanFilterDaoHibernate() {
        super(GzAccountPlanFilter.class);
    }
    
    public JQueryPager getGzAccountPlanFliterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("filterId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzAccountPlanFilter.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzAccountPlanFliterCriteria", e);
			return paginatedList;
		}

	}
}
