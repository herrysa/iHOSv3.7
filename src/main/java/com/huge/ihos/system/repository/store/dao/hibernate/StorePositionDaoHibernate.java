package com.huge.ihos.system.repository.store.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.repository.store.dao.StorePositionDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("storePositionDao")
public class StorePositionDaoHibernate extends
		GenericDaoHibernate<StorePosition, String> implements StorePositionDao {

	public StorePositionDaoHibernate() {
		super(StorePosition.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getStorePositionCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("posId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, StorePosition.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStorePositionCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoresByStorePosition(Store store) {
		List<Store> stores = null;
		try {
			String hql = "from " + this.getPersistentClass().getSimpleName()
					+ " where storeId=?";
			stores = this.getHibernateTemplate().find(hql, store.getId());
		} catch (Exception e) {
			log.error("getStoresByStorePosition", e);
			e.printStackTrace();
		}
		return stores;
	}

}
