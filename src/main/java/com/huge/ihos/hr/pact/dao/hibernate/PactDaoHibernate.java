package com.huge.ihos.hr.pact.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.dao.PactDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("pactDao")
public class PactDaoHibernate extends GenericDaoHibernate<Pact, String> implements PactDao {

    public PactDaoHibernate() {
        super(Pact.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getPactCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Pact.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPactCriteria", e);
			return paginatedList;
		}
	}
}
