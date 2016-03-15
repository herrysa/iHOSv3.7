package com.huge.ihos.nursescore.dao.hibernate;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.nursescore.dao.NurseDayScoreDetailDao;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("nurseDayScoreDetailDao")
public class NurseDayScoreDetailDaoHibernate extends GenericDaoHibernate<NurseDayScoreDetail, Long> implements NurseDayScoreDetailDao {

    public NurseDayScoreDetailDaoHibernate() {
        super(NurseDayScoreDetail.class);
    }
    
    public JQueryPager getNurseDayScoreDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("dayScoreDetailID");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, NurseDayScoreDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getNurseDayScoreDetailCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List addByDeptId(String deptId, String checkPeriod,String dayScoreID) {
		String sql1 = "";
		String sql="select personid,yearcode from jj_t_monthNurseList where checkPeriod='"+checkPeriod+"' and ownerdeptid='"+deptId+"'";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		List list = query.list();
		return list;
	}

	@Override
	public List<NurseDayScoreDetail> selectedPerson(Date score, String deptId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(NurseDayScoreDetail.class);
		criteria.add(Restrictions.eq("scoredate", score));
		criteria.add(Restrictions.eq("groupid.departmentId", deptId));
		return criteria.list();
	}

	@Override
	public List<NurseDayScoreDetail> findByNurseDayScore(
			NurseDayScore nurseDayScore) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("dayScoreID", nurseDayScore));
		return criteria.list();
	}
}
