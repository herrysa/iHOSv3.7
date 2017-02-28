package com.huge.ihos.system.configuration.proj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.proj.dao.ProjNatureDao;
import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("projNatureDao")
public class ProjNatureDaoHibernate extends GenericDaoHibernate<ProjNature, String> implements ProjNatureDao {

    public ProjNatureDaoHibernate() {
        super(ProjNature.class);
    }
    
    public JQueryPager getProjNatureCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("natureId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ProjNature.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProjNatureCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ProjNature> getAllEnabled() {
		Criteria criteria =  this.getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		return criteria.list();
	}
}
