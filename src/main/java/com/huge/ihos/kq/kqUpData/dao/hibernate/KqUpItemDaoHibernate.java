package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqUpItemDao")
public class KqUpItemDaoHibernate extends GenericDaoHibernate<KqUpItem, String> implements KqUpItemDao {

    public KqUpItemDaoHibernate() {
        super(KqUpItem.class);
    }
    
    public JQueryPager getKqUpItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("itemId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqUpItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqUpItemCriteria", e);
			return paginatedList;
		}

	}
}
