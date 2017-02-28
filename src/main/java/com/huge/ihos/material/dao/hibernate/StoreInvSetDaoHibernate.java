package com.huge.ihos.material.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.StoreInvSetDao;
import com.huge.ihos.material.model.StoreInvSet;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("storeInvSetDao")
public class StoreInvSetDaoHibernate extends GenericDaoHibernate<StoreInvSet, String> implements StoreInvSetDao {

    public StoreInvSetDaoHibernate() {
        super(StoreInvSet.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getStoreInvSetCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("inventoryDict.invCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, StoreInvSet.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStoreInvSetCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreInvSet> getStoreInvSetsByColumn(String columnName,
			String value) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where "+columnName+" = ?";
		List<StoreInvSet> list = this.getHibernateTemplate().find( hql,value);
		return list;
	}
	
	
	
	
    
    
}
