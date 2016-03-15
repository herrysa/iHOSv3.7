package com.huge.ihos.hr.pact.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.pact.model.PactRenew;
import com.huge.ihos.hr.pact.dao.PactRenewDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("pactRenewDao")
public class PactRenewDaoHibernate extends GenericDaoHibernate<PactRenew, String> implements PactRenewDao {

    public PactRenewDaoHibernate() {
        super(PactRenew.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getPactRenewCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PactRenew.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPactRenewCriteria", e);
			return paginatedList;
		}

	}
}
