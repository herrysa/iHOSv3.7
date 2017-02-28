package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.model.InvUse;
import com.huge.ihos.material.dao.InvUseDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invUseDao")
public class InvUseDaoHibernate extends GenericDaoHibernate<InvUse, String> implements InvUseDao {

	public InvUseDaoHibernate() {
		super(InvUse.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getInvUseCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("invUseId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvUse.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvUseCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvUse> getAllByCO(String copycode, String orgcode) {
		String hql = " from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and copyCode=?";
		Object[] args = { orgcode, copycode };
		return this.getHibernateTemplate().find(hql, args);
	}
}
