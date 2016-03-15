package com.huge.ihos.update.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.update.dao.JjUpdataDefColumnDao;
import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("jjUpdataDefColumnDao")
public class JjUpdataDefColumnDaoHibernate extends GenericDaoHibernate<JjUpdataDefColumn, Long> implements JjUpdataDefColumnDao {

    public JjUpdataDefColumnDaoHibernate() {
        super(JjUpdataDefColumn.class);
    }
    
    public JQueryPager getJjUpdataDefColumnCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("columnId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, JjUpdataDefColumn.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getJjUpdataDefColumnCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<JjUpdataDefColumn> getEnabledByOrder() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JjUpdataDefColumn.class);
		criteria.add(Restrictions.eq("disabled", false));
		criteria.addOrder(Order.asc("order"));
		return criteria.list();
	}
}
