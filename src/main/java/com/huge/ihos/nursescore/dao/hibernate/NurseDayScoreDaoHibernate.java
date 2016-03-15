package com.huge.ihos.nursescore.dao.hibernate;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.dao.NurseDayScoreDao;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("nurseDayScoreDao")
public class NurseDayScoreDaoHibernate extends GenericDaoHibernate<NurseDayScore, Long> implements NurseDayScoreDao {

    public NurseDayScoreDaoHibernate() {
        super(NurseDayScore.class);
    }
    
    public JQueryPager getNurseDayScoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("dayScoreID");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, NurseDayScore.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getNurseDayScoreCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<NurseDayScore> findByScoreDate(String deptId,Date date) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("scoredate", date));
		criteria.add(Restrictions.eq("groupid.departmentId", deptId));
		return criteria.list();
	}
}
