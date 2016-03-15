package com.huge.ihos.nursescore.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.dao.MonthNurseDao;
import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("monthNurseDao")
public class MonthNurseDaoHibernate extends GenericDaoHibernate<MonthNurse, Long> implements MonthNurseDao {

    public MonthNurseDaoHibernate() {
        super(MonthNurse.class);
    }
    
    public JQueryPager getMonthNurseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, MonthNurse.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getMonthNurseCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<MonthNurse> getByCheckPeriodAndDept(String checkPeriod,String deptId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MonthNurse.class);
		criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		criteria.add(Restrictions.eq("ownerdeptid.departmentId", deptId));
		return criteria.list();
	}
}
