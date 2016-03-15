package com.huge.ihos.system.systemManager.searchButtonPriv.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("buttonPrivDao")
public class ButtonPrivDaoHibernate extends GenericDaoHibernate<ButtonPriv, String> implements ButtonPrivDao {

    public ButtonPrivDaoHibernate() {
        super(ButtonPriv.class);
    }
    
    public JQueryPager getButtonPrivCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("privId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ButtonPriv.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getButtonPrivCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ButtonPriv> findByRole(String roleId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("roleId", roleId));
		return criteria.list();
	}

	@Override
	public ButtonPriv findByRoleAndSearch(String roleId, String searchId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq("searchOrMenuId", searchId));
		List<ButtonPriv> l = (List<ButtonPriv>)criteria.list();
		if(l==null || l.size()!=1){
			return null;
		}else{
			return l.get(0);
		}
	}
	
	
}
