package com.huge.ihos.system.systemManager.period.dao.hibernate;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.period.dao.PeriodMonthDao;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("periodMonthDao")
public class PeriodMonthDaoHibernate extends GenericDaoHibernate<PeriodMonth, String> implements PeriodMonthDao {

    public PeriodMonthDaoHibernate() {
        super(PeriodMonth.class);
    }
    
    public JQueryPager getPeriodMonthCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("periodMonthCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PeriodMonth.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPeriodMonthCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public Boolean removeByPeriodId(String periodId) {
		try{
			String sql = "delete from sy_periodMonth where periodMonthId = '"+periodId+"'";
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			session.createSQLQuery(sql).executeUpdate();
			return true;
		} catch (Exception e){
			log.error("getPeriodMonthCriteria", e);
			return false;
		}
		
	}

	@Override
	public List<PeriodMonth> getMonthByPlanAndYear(String planId, String year) {
		/*String hql = "from PeriodMonth where periodYear.plan.planId='"+planId+"' and periodYear.periodYearCode='"+year+"' order by periodMonthCode asc";
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		return session.createQuery(hql).list();*/
		Criteria criteria = this.getCriteria();
		criteria.createAlias("periodYear", "periodYear");
		criteria.add(Restrictions.eq("periodYear.plan.planId", planId));
		criteria.add(Restrictions.eq("periodYear.periodYearCode", year));
		criteria.addOrder(Order.asc("periodMonthCode"));
		List<PeriodMonth> periodMonths = criteria.list();
		return periodMonths;
	}

	@Override
	public PeriodMonth getPeriodMonth(String planId,Date optDate) {
		Criteria criteria = this.getCriteria();
		criteria.createAlias("periodYear", "periodYear");
		criteria.add(Restrictions.eq("periodYear.plan.planId", planId));
		//criteria.add(Restrictions.eq("periodYear.periodYearCode", kjYear));
		criteria.add(Restrictions.ge("endDate", optDate));
		criteria.add(Restrictions.le("beginDate", optDate));
		List<PeriodMonth> periodMonths = criteria.list();
		if(periodMonths.size()>0){
			return periodMonths.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<PeriodMonth> getMonthListBetweenPeriods(String beginPeriod,
			String endPeriod, String planId) {
		/*String hql = "from PeriodMonth where plan.planId='"+planId+"' and month>='"+beginPeriod+"' and month <= '"+endPeriod+"' order by month asc";
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		return session.createQuery(hql).list();*/
		
		Criteria criteria = this.getCriteria();
		criteria.createAlias("periodYear", "periodYear");
		criteria.add(Restrictions.eq("periodYear.plan.planId", planId));
		//criteria.add(Restrictions.eq("periodYear.periodYearCode", kjYear));
		criteria.add(Restrictions.ge("periodMonthCode", beginPeriod));
		criteria.add(Restrictions.le("periodMonthCode", endPeriod));
		criteria.addOrder(Order.asc("periodMonthCode"));
		List<PeriodMonth> periodMonths = criteria.list();
		return periodMonths;
	}

	@Override
	public List<PeriodMonth> getLessCurrentPeriod(String currentPeriod) {
		String planCode = UserContextUtil.getUserContext().getPeriodPlanCode();
		Criteria criteria = this.getCriteria();
		criteria.createAlias("periodYear", "periodYear");
		criteria.add(Restrictions.eq("periodYear.plan.planId", planCode));
		criteria.add(Restrictions.le("periodMonthCode", currentPeriod));
		criteria.addOrder(Order.asc("periodMonthCode"));
		return criteria.list();
	}

/*	@Override
	public List<PeriodMonth> getPeriodsBetween(String beginPeriod,
			String endPeriod) {
		SystemVariable systemVariable = RequestContext.getCurrentRequestContext().getSystemVariable();
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("plan.planId", systemVariable.getPeriodPlanId()));
		criteria.add(Restrictions.ge("periodMonthCode", beginPeriod));
		criteria.add(Restrictions.le("periodMonthCode", endPeriod));
		criteria.addOrder(Order.asc("periodMonthCode"));
		return criteria.list();
	}*/

	@Override
	public PeriodMonth getPeriodByCode(String periodCode) {
		String planCode = UserContextUtil.getUserContext().getPeriodPlanCode();
		Criteria criteria = this.getCriteria();
		criteria.createAlias("periodYear", "periodYear");
		criteria.add(Restrictions.eq("periodYear.plan.planId", planCode));
		criteria.add(Restrictions.eq("periodMonthCode", periodCode));
		List<PeriodMonth> pl = criteria.list();
		if(pl!=null&&pl.size()>0){
			return pl.get(0);
		}
		return null;
	}
}
