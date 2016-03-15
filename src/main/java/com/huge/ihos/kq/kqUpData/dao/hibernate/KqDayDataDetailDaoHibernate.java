package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.ihos.kq.kqUpData.dao.KqDayDataDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqDayDataDetailDao")
public class KqDayDataDetailDaoHibernate extends GenericDaoHibernate<KqDayDataDetail, String> implements KqDayDataDetailDao {

    public KqDayDataDetailDaoHibernate() {
        super(KqDayDataDetail.class);
    }
    
    public JQueryPager getKqDayDataDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("detailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqDayDataDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqDayDataDetailCriteria", e);
			return paginatedList;
		}

	}
}
