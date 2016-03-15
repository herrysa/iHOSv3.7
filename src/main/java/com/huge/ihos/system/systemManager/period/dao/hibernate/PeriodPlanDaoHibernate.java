package com.huge.ihos.system.systemManager.period.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.period.dao.PeriodPlanDao;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("periodPlanDao")
public class PeriodPlanDaoHibernate extends GenericDaoHibernate<PeriodPlan, String> implements PeriodPlanDao {

    public PeriodPlanDaoHibernate() {
        super(PeriodPlan.class);
    }
    
    public JQueryPager getPeriodPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("planId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PeriodPlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPeriodPlanCriteria", e);
			return paginatedList;
		}

	}
    /**
     * 重写get方法，解决添加时查询报错的问题
     */
    public PeriodPlan get(String id) {
    	return this.hibernateTemplate.get(PeriodPlan.class, id);
    }
    
    @Override
    public PeriodPlan getDefaultPeriodPlan() {
    	Criteria criteria = getCriteria();
    	List<PeriodPlan> periodPlans = criteria.add(Restrictions.eq("isDefault", true)).list();
    	if(periodPlans != null && periodPlans.size() > 0) {
    		return periodPlans.get(0);
    	}
    	return null;
    }
}
