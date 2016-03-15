package com.huge.ihos.system.systemManager.searchButtonPriv.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("buttonPrivDetailDao")
public class ButtonPrivDetailDaoHibernate extends GenericDaoHibernate<ButtonPrivDetail, String> implements ButtonPrivDetailDao {

    public ButtonPrivDetailDaoHibernate() {
        super(ButtonPrivDetail.class);
    }
    
    public JQueryPager getButtonPrivDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bpDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ButtonPrivDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getButtonPrivDetailCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ButtonPrivDetail> findNoRightPrivDetail(String roleId,
			String searchId) {
		Criteria criteria =this.getCriteria();
		criteria.createAlias("buttonPriv", "buttonPriv");
		criteria.add(Restrictions.eq("buttonPriv.roleId", roleId));
		criteria.add(Restrictions.eq("buttonPriv.searchOrMenuId", searchId));
//		criteria.add(Restrictions.eq("right", false));
		return criteria.list();
	}
}
