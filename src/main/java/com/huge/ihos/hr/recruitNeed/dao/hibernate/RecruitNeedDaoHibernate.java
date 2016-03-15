package com.huge.ihos.hr.recruitNeed.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.ihos.hr.recruitNeed.dao.RecruitNeedDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("recruitNeedDao")
public class RecruitNeedDaoHibernate extends GenericDaoHibernate<RecruitNeed, String> implements RecruitNeedDao {

    public RecruitNeedDaoHibernate() {
        super(RecruitNeed.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getRecruitNeedCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, RecruitNeed.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getRecruitNeedCriteria", e);
			return paginatedList;
		}

	}
}
