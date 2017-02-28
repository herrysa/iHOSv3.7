package com.huge.ihos.material.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.model.InvInStore;
import com.huge.ihos.material.dao.InvInStoreDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("invInStoreDao")
public class InvInStoreDaoHibernate extends GenericDaoHibernate<InvInStore, String> implements InvInStoreDao {

    public InvInStoreDaoHibernate() {
        super(InvInStore.class);
    }
    
    public JQueryPager getInvInStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvInStore.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvInStoreCriteria", e);
			return paginatedList;
		}

	}
}
