package com.huge.ihos.update.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.update.dao.WorkScoreDao;
import com.huge.ihos.update.model.WorkScore;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("workScoreDao")
public class WorkScoreDaoHibernate extends GenericDaoHibernate<WorkScore, String> implements WorkScoreDao {

    public WorkScoreDaoHibernate() {
        super(WorkScore.class);
    }
    
    public JQueryPager getWorkScoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, WorkScore.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getWorkScoreCriteria", e);
			return paginatedList;
		}

	}
}
