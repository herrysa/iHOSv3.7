package com.huge.ihos.hr.recruitPlan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.hr.recruitPlan.dao.RecruitPlanDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("recruitPlanDao")
public class RecruitPlanDaoHibernate extends GenericDaoHibernate<RecruitPlan, String> implements RecruitPlanDao {

    public RecruitPlanDaoHibernate() {
        super(RecruitPlan.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getRecruitPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, RecruitPlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getRecruitPlanCriteria", e);
			return paginatedList;
		}

	}
}
