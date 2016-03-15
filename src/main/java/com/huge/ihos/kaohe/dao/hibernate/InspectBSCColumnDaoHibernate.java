package com.huge.ihos.kaohe.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kaohe.dao.InspectBSCColumnDao;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("inspectBSCColumnDao")
public class InspectBSCColumnDaoHibernate extends GenericDaoHibernate<InspectBSCColumn, Long> implements InspectBSCColumnDao {

    public InspectBSCColumnDaoHibernate() {
        super(InspectBSCColumn.class);
    }
    
    public JQueryPager getInspectBSCColumnCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InspectBSCColumn.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInspectBSCColumnCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
	public List<InspectBSCColumn> findByInspectTemplId(String inspectTemplId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(InspectBSCColumn.class);
		criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		criteria.addOrder(Order.asc("disOrder"));
		return criteria.list();
	}

	@Override
	public void delByInspectTemplId(String inspectTemplId) {
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete InspectBSCColumn as inspectBSCColumn where inspectBSCColumn.inspectTempl.inspectModelId = '"+inspectTemplId+"'").executeUpdate();
	}
}
