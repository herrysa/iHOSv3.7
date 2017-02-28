package com.huge.ihos.system.configuration.proj.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.proj.dao.ProjDao;
import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("projDao")
public class ProjDaoHibernate extends GenericDaoHibernate<Proj, String> implements ProjDao {

    public ProjDaoHibernate() {
        super(Proj.class);
    }
    
    public JQueryPager getProjCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("projId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Proj.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getProjCriteria", e);
			return paginatedList;
		}

	}
}
