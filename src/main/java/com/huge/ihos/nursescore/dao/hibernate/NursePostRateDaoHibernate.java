package com.huge.ihos.nursescore.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.ihos.nursescore.dao.NursePostRateDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("nursePostRateDao")
public class NursePostRateDaoHibernate extends GenericDaoHibernate<NursePostRate, String> implements NursePostRateDao {

    public NursePostRateDaoHibernate() {
        super(NursePostRate.class);
    }
    
    public JQueryPager getNursePostRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("code");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, NursePostRate.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getNursePostRateCriteria", e);
			return paginatedList;
		}

	}
}
