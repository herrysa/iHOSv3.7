package com.huge.ihos.system.systemManager.period.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.period.dao.MoudlePeriodDao;
import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("moudlePeriodDao")
public class MoudlePeriodDaoHibernate extends GenericDaoHibernate<MoudlePeriod, String> implements MoudlePeriodDao {

    public MoudlePeriodDaoHibernate() {
        super(MoudlePeriod.class);
    }
    
    public JQueryPager getMoudlePeriodCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("moudlePeriodId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, MoudlePeriod.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getMoudlePeriodCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public void removeBySubId(String periodSubject_periodId) {
		String sql = "delete from sy_moudle_period where periodId = '"+periodSubject_periodId+"'";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public List<MoudlePeriod> getMoudlePeriod(String planId, String periodSubId,
			String moudleId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("planId", planId));
		criteria.add(Restrictions.eq("periodId", periodSubId));
		criteria.add(Restrictions.eq("menuId", moudleId));
		criteria.addOrder(Order.asc("month"));
		List<MoudlePeriod> moudlePeriods = criteria.list();
		return moudlePeriods;
	}
}
