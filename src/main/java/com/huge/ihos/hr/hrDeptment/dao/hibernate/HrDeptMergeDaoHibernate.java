package com.huge.ihos.hr.hrDeptment.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.ihos.hr.hrDeptment.dao.HrDeptMergeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrDeptMergeDao")
public class HrDeptMergeDaoHibernate extends GenericDaoHibernate<HrDeptMerge, String> implements HrDeptMergeDao {

    public HrDeptMergeDaoHibernate() {
        super(HrDeptMerge.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrDeptMergeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDeptMerge.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDeptMergeCriteria", e);
			return paginatedList;
		}

	}
}
