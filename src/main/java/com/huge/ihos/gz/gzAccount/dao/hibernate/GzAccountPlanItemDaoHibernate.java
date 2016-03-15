package com.huge.ihos.gz.gzAccount.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzAccountPlanItemDao")
public class GzAccountPlanItemDaoHibernate extends GenericDaoHibernate<GzAccountPlanItem, String> implements GzAccountPlanItemDao {

    public GzAccountPlanItemDaoHibernate() {
        super(GzAccountPlanItem.class);
    }
    
    public JQueryPager getGzAccountPlanItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("colId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzAccountPlanItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzAccountPlanItemCriteria", e);
			return paginatedList;
		}

	}
}
