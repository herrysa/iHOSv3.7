package com.huge.ihos.nursescore.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.model.NurseYearRate;
import com.huge.ihos.nursescore.dao.NurseYearRateDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("nurseYearRateDao")
public class NurseYearRateDaoHibernate extends GenericDaoHibernate<NurseYearRate, String> implements NurseYearRateDao {

    public NurseYearRateDaoHibernate() {
        super(NurseYearRate.class);
    }
    
    public JQueryPager getNurseYearRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("code");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, NurseYearRate.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getNurseYearRateCriteria", e);
			return paginatedList;
		}

	}
}
