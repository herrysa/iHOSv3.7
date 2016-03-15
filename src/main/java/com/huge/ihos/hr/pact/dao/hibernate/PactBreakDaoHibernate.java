package com.huge.ihos.hr.pact.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.pact.model.PactBreak;
import com.huge.ihos.hr.pact.dao.PactBreakDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("pactBreakDao")
public class PactBreakDaoHibernate extends GenericDaoHibernate<PactBreak, String> implements PactBreakDao {

    public PactBreakDaoHibernate() {
        super(PactBreak.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getPactBreakCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PactBreak.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPactBreakCriteria", e);
			return paginatedList;
		}

	}
}
