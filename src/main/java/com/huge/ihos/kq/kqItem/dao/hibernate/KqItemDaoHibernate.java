package com.huge.ihos.kq.kqItem.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.dao.KqItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqItemDao")
public class KqItemDaoHibernate extends GenericDaoHibernate<KqItem, String> implements KqItemDao {

    public KqItemDaoHibernate() {
        super(KqItem.class);
    }
    
    public JQueryPager getKqItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("kqItemId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqItemCriteria", e);
			return paginatedList;
		}

	}
}
