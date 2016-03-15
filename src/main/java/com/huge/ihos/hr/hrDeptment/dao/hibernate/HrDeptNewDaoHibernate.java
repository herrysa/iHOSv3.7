package com.huge.ihos.hr.hrDeptment.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.ihos.hr.hrDeptment.dao.HrDeptNewDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrDeptNewDao")
public class HrDeptNewDaoHibernate extends GenericDaoHibernate<HrDeptNew, String> implements HrDeptNewDao {

    public HrDeptNewDaoHibernate() {
        super(HrDeptNew.class);
    }
    
    public JQueryPager getHrDeptNewCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDeptNew.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDeptNewCriteria", e);
			return paginatedList;
		}

	}
}
