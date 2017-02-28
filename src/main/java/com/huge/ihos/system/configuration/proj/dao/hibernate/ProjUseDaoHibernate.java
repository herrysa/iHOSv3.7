package com.huge.ihos.system.configuration.proj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.proj.dao.ProjUseDao;
import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("projUseDao")
public class ProjUseDaoHibernate extends GenericDaoHibernate<ProjUse, String> implements ProjUseDao {

    public ProjUseDaoHibernate() {
        super(ProjUse.class);
    }
    
    public JQueryPager getProjUseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("useCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ProjUse.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProjUseCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ProjUse> getAllEnabled() {
		Criteria criteria =  this.getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		return criteria.list();
	}
}
