package com.huge.ihos.system.configuration.proj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.proj.dao.ProjTypeDao;
import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("projTypeDao")
public class ProjTypeDaoHibernate extends GenericDaoHibernate<ProjType, String> implements ProjTypeDao {

    public ProjTypeDaoHibernate() {
        super(ProjType.class);
    }
    
    public JQueryPager getProjTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("typeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ProjType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProjTypeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ProjType> getAllEnabled() {
		Criteria criteria =  this.getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		return criteria.list();
	}
}
