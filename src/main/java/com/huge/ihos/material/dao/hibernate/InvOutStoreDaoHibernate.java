package com.huge.ihos.material.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.model.InvOutStore;
import com.huge.ihos.material.dao.InvOutStoreDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("invOutStoreDao")
public class InvOutStoreDaoHibernate extends GenericDaoHibernate<InvOutStore, String> implements InvOutStoreDao {

    public InvOutStoreDaoHibernate() {
        super(InvOutStore.class);
    }
    
    public JQueryPager getInvOutStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, InvOutStore.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvOutStoreCriteria", e);
			return paginatedList;
		}

	}
}
