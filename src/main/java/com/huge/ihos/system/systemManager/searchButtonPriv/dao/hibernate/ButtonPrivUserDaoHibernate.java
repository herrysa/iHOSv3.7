package com.huge.ihos.system.systemManager.searchButtonPriv.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("buttonPrivUserDao")
public class ButtonPrivUserDaoHibernate extends GenericDaoHibernate<ButtonPrivUser, String> implements ButtonPrivUserDao {

    public ButtonPrivUserDaoHibernate() {
        super(ButtonPrivUser.class);
    }
    
    public JQueryPager getButtonPrivUserCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("privId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ButtonPrivUser.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getButtonPrivUserCriteria", e);
			return paginatedList;
		}

	}
    @Override
	public List<ButtonPrivUser> findByUser(String userId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		return criteria.list();
	}
}
