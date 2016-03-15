package com.huge.ihos.hr.recruitResume.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.recruitResume.model.RecruitResume;
import com.huge.ihos.hr.recruitResume.dao.RecruitResumeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("recruitResumeDao")
public class RecruitResumeDaoHibernate extends GenericDaoHibernate<RecruitResume, String> implements RecruitResumeDao {

    public RecruitResumeDaoHibernate() {
        super(RecruitResume.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getRecruitResumeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, RecruitResume.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getRecruitResumeCriteria", e);
			return paginatedList;
		}

	}
}
