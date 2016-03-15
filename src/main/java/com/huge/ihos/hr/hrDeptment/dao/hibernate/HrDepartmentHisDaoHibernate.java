package com.huge.ihos.hr.hrDeptment.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentHisDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDeptSnapPk;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrDepartmentHisDao")
public class HrDepartmentHisDaoHibernate extends GenericDaoHibernate<HrDepartmentHis, HrDeptSnapPk> implements HrDepartmentHisDao {

    public HrDepartmentHisDaoHibernate() {
        super(HrDepartmentHis.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrDepartmentHisCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("hrDeptPk");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDepartmentHis.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDepartmentHisCriteria", e);
			return paginatedList;
		}

	}
}
