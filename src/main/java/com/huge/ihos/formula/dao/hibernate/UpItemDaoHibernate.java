package com.huge.ihos.formula.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.formula.dao.UpItemDao;
import com.huge.ihos.formula.model.UpCost;
import com.huge.ihos.formula.model.UpItem;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("upItemDao")
public class UpItemDaoHibernate extends GenericDaoHibernate<UpItem, Long> implements UpItemDao {

    public UpItemDaoHibernate() {
        super(UpItem.class);
    }
    
    public JQueryPager getUpItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, UpItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getUpItemCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<UpItem> getUpItemsByDept(String deptId,String upItemClass) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("sbdeptId.departmentId", deptId),Restrictions.isNull("sbdeptId.departmentId")));
		criteria.add(Restrictions.eq("itemClass",upItemClass));
		return criteria.list();
	}

	@Override
	public boolean validataUpCost(Long upItemId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria( UpCost.class );
		criteria.add(Restrictions.eq("upitemid.id", upItemId));
		List list=criteria.list();
		if (list.size()>0) {
			return true;
		}
		return false;
	}
    
}
