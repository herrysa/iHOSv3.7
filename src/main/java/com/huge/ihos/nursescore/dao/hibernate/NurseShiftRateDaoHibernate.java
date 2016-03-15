package com.huge.ihos.nursescore.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.ihos.nursescore.dao.NurseShiftRateDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("nurseShiftRateDao")
public class NurseShiftRateDaoHibernate extends GenericDaoHibernate<NurseShiftRate, String> implements NurseShiftRateDao {

    public NurseShiftRateDaoHibernate() {
        super(NurseShiftRate.class);
    }
    
    public JQueryPager getNurseShiftRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("code");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, NurseShiftRate.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getNurseShiftRateCriteria", e);
			return paginatedList;
		}

	}
}
