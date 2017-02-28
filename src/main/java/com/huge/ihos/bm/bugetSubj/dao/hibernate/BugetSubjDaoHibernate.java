package com.huge.ihos.bm.bugetSubj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.bugetSubj.dao.BugetSubjDao;
import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bugetSubjDao")
public class BugetSubjDaoHibernate extends GenericDaoHibernate<BugetSubj, String> implements BugetSubjDao {

    public BugetSubjDaoHibernate() {
        super(BugetSubj.class);
    }
    
    public JQueryPager getBugetSubjCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bugetSubjId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BugetSubj.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBugetSubjCriteria", e);
			return paginatedList;
		}

	}
}
