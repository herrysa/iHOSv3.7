package com.huge.ihos.kaohe.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kaohe.dao.InspectBSCDao;
import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("inspectBSCDao")
public class InspectBSCDaoHibernate extends GenericDaoHibernate<InspectBSC, String> implements InspectBSCDao {

    public InspectBSCDaoHibernate() {
        super(InspectBSC.class);
    }
    
    public JQueryPager getInspectBSCCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("inspectContentId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InspectBSC.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInspectBSCCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public InspectBSC findByKpiItem(KpiItem kpiItem,String inspectTemplId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(InspectBSC.class);
		criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		criteria.add(Restrictions.eq("kpiItem", kpiItem));
		List<InspectBSC> InspectBSCList = criteria.list();
		InspectBSC inspectBSC = null;
		if(InspectBSCList!=null&&InspectBSCList.size()!=0){
			inspectBSC = InspectBSCList.get(0);
		}
		return inspectBSC;
	}

	@Override
	public List<InspectBSC> findByInspectTemplId(String inspectTemplId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(InspectBSC.class);
		criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		return criteria.list();
	}

	@Override
	public void deleteByInspectTemplId(String inspectTemplId) {
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete InspectBSC as inspectBSC where inspectBSC.inspectTempl.inspectModelId = '"+inspectTemplId+"'").executeUpdate();
	}
	
	@Override
	public List<InspectBSC> findByInspectByDept(String inspectTemplId,
			Department dept) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(InspectBSC.class);
		criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		criteria.add(Restrictions.eq("department", dept));
		return criteria.list();
	}
}
