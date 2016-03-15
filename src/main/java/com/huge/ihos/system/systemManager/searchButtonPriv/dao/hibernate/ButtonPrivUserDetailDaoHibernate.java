package com.huge.ihos.system.systemManager.searchButtonPriv.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("buttonPrivUserDetailDao")
public class ButtonPrivUserDetailDaoHibernate extends GenericDaoHibernate<ButtonPrivUserDetail, String> implements ButtonPrivUserDetailDao {

    public ButtonPrivUserDetailDaoHibernate() {
        super(ButtonPrivUserDetail.class);
    }
    
    public JQueryPager getButtonPrivUserDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bpuDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ButtonPrivUserDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getButtonPrivUserDetailCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ButtonPrivUserDetail> findNoRightPrivDetail(
			String userId, String searchId) {
		Criteria criteria =this.getCriteria();
		criteria.createAlias("buttonPrivUser", "buttonPrivUser");
		criteria.add(Restrictions.eq("buttonPrivUser.userId", userId));
		criteria.add(Restrictions.eq("buttonPrivUser.searchOrMenuId", searchId));
//		criteria.add(Restrictions.eq("right", false));
		return criteria.list();
	}

	@Override
	public List<ButtonPrivUserDetail> findByUserAndSearch(String userId,
			String searchId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sql = "select * from t_buttonPrivUser_detail where buttonPrivUserId in "
					+ "( select privId from t_buttonPrivUser where userId='"+userId+"' and searchOrMenuId='"+searchId+"' )";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(ButtonPrivUserDetail.class);
		return query.list();
	}
	
	
}
