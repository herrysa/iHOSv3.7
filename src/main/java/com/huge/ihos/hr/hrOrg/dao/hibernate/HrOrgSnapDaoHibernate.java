package com.huge.ihos.hr.hrOrg.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrOrg.dao.HrOrgSnapDao;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("hrOrgSnapDao")
public class HrOrgSnapDaoHibernate extends GenericDaoHibernate<HrOrgSnap, String> implements HrOrgSnapDao {

    public HrOrgSnapDaoHibernate() {
        super(HrOrgSnap.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrOrgSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("snapId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrOrgSnap.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrOrgSnapCriteria", e);
			return paginatedList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getHisSnapIdList(String snapCode) {
		String hql = "select max(snapId) from HrOrgSnap where snapCode <=? and orgCode <> ? group by orgCode";
		return this.getHibernateTemplate().find( hql,snapCode,"XT");
	}
    
}
