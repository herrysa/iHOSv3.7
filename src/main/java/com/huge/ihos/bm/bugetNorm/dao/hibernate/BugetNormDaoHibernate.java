package com.huge.ihos.bm.bugetNorm.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.bugetNorm.model.BugetNorm;
import com.huge.ihos.bm.bugetNorm.dao.BugetNormDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bugetNormDao")
public class BugetNormDaoHibernate extends GenericDaoHibernate<BugetNorm, String> implements BugetNormDao {

    public BugetNormDaoHibernate() {
        super(BugetNorm.class);
    }
    
    public JQueryPager getBugetNormCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("normId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BugetNorm.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBugetNormCriteria", e);
			return paginatedList;
		}

	}
}
