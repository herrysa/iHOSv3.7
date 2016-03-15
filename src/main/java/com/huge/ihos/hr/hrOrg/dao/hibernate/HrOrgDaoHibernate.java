package com.huge.ihos.hr.hrOrg.dao.hibernate;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.dao.HrOrgDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("hrOrgDao")
public class HrOrgDaoHibernate extends GenericDaoHibernate<HrOrg, String> implements HrOrgDao {

    public HrOrgDaoHibernate() {
        super(HrOrg.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrOrgCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("orgCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrOrg.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrOrgCriteria", e);
			return paginatedList;
		}
	}
}
