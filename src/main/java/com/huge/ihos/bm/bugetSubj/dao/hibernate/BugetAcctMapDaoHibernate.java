package com.huge.ihos.bm.bugetSubj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.bugetSubj.dao.BugetAcctMapDao;
import com.huge.ihos.bm.bugetSubj.model.BugetAcctMap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bugetAcctMapDao")
public class BugetAcctMapDaoHibernate extends GenericDaoHibernate<BugetAcctMap, String> implements BugetAcctMapDao {

    public BugetAcctMapDaoHibernate() {
        super(BugetAcctMap.class);
    }
    
    public JQueryPager getBugetAcctMapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("mapId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BugetAcctMap.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBugetAcctMapCriteria", e);
			return paginatedList;
		}

	}
}
