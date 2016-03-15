package com.huge.ihos.system.systemManager.period.dao.hibernate;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.period.dao.PeriodYearDao;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("periodYearDao")
public class PeriodYearDaoHibernate extends GenericDaoHibernate<PeriodYear, String> implements PeriodYearDao {

    public PeriodYearDaoHibernate() {
        super(PeriodYear.class);
    }
    
    public JQueryPager getPeriodYearCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("periodYearId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PeriodYear.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPeriodYearCriteria", e);
			return paginatedList;
		}

	}
	@Override
	public PeriodYear getLastYearByPlan(String planId) {
		/*String sql = "select top 1 * from t_periodYear where planId = '"+planId+"' order by endDate DESC ";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql).addEntity(PeriodYear.class);*/
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("plan.planId", planId));
		criteria.addOrder(Order.desc("endDate"));
		List list = criteria.list();
		if(list == null || list.size() ==0){
			return null;
		} else {
			return (PeriodYear) list.get(0);
		}
	}

	@Override
	public PeriodYear getPeriodYearByPlanAndYear(String planId, String year) {
		/*String hql = "from PeriodSubject where plan.planId='"+planId+"' and periodYear='"+year+"'";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<PeriodYear> subList = session.createQuery(hql).list();*/
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("plan.planId", planId));
		criteria.add(Restrictions.eq("periodYearCode", year));
		//criteria.addOrder(Order.desc("endDate"));
		List list = criteria.list();
		if(list!=null&& list.size() > 0){
			return (PeriodYear)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PeriodYear getPeriodYearByPlanAndDate(String planId, Date date) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("plan.planId", planId));
		criteria.add(Restrictions.le("beginDate", date));
		criteria.add(Restrictions.ge("endDate", date));
		
		List<PeriodYear> list = criteria.list();
		if(list!=null&& list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
