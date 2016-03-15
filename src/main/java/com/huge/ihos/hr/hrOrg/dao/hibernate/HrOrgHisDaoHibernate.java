package com.huge.ihos.hr.hrOrg.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrOrg.dao.HrOrgHisDao;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgPk;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("hrOrgHisDao")
public class HrOrgHisDaoHibernate extends GenericDaoHibernate<HrOrgHis, HrOrgPk> implements HrOrgHisDao {

    public HrOrgHisDaoHibernate() {
        super(HrOrgHis.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrOrgHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("hrOrgPk");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrOrgHis.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrOrgHisCriteria", e);
			return paginatedList;
		}
	}
}
