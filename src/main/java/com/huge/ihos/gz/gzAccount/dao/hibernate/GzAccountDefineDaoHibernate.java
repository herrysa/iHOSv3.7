package com.huge.ihos.gz.gzAccount.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.dao.GzAccountDefineDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzAccountDefineDao")
public class GzAccountDefineDaoHibernate extends GenericDaoHibernate<GzAccountDefine, String> implements GzAccountDefineDao {

    public GzAccountDefineDaoHibernate() {
        super(GzAccountDefine.class);
    }
    
    public JQueryPager getGzAccountDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("defineId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzAccountDefine.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzAccountDefineCriteria", e);
			return paginatedList;
		}

	}
}
