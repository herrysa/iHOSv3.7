package com.huge.ihos.gz.gzItemPersonType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzItemPersonType.model.GzItemPersonType;
import com.huge.ihos.gz.gzItemPersonType.dao.gzItemPersonTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzItemPersonTypeDao")
public class gzItemPersonTypeDaoHibernate extends GenericDaoHibernate<GzItemPersonType, String> implements gzItemPersonTypeDao {

    public gzItemPersonTypeDaoHibernate() {
        super(GzItemPersonType.class);
    }
    
    public JQueryPager getgzItemPersonTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("mappingId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzItemPersonType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getgzItemPersonTypeCriteria", e);
			return paginatedList;
		}

	}
}
