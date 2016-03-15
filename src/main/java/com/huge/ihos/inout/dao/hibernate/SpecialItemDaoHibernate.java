package com.huge.ihos.inout.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.model.SpecialItem;
import com.huge.ihos.inout.dao.SpecialItemDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("specialItemDao")
public class SpecialItemDaoHibernate extends GenericDaoHibernate<SpecialItem, String> implements SpecialItemDao {

    public SpecialItemDaoHibernate() {
        super(SpecialItem.class);
    }
    
    public JQueryPager getSpecialItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("itemId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SpecialItem.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSpecialItemCriteria", e);
			return paginatedList;
		}

	}
}
