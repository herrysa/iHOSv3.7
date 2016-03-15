package com.huge.ihos.hr.hrPerson.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrPerson.dao.HrPersonHisDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonPK;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("hrPersonHisDao")
public class HrPersonHisDaoHibernate extends GenericDaoHibernate<HrPersonHis, HrPersonPK> implements HrPersonHisDao {

    public HrPersonHisDaoHibernate() {
        super(HrPersonHis.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrPersonHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("hrPersonPk");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrPersonHis.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrPersonHisCriteria", e);
			return paginatedList;
		}

	}
}
