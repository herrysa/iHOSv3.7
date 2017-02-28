package com.huge.ihos.bm.budgetModel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.bm.budgetModel.model.BmModelAssist;
import com.huge.ihos.bm.budgetModel.dao.BmModelAssistDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bmModelAssistDao")
public class BmModelAssistDaoHibernate extends GenericDaoHibernate<BmModelAssist, String> implements BmModelAssistDao {

    public BmModelAssistDaoHibernate() {
        super(BmModelAssist.class);
    }
    
    public JQueryPager getBmModelAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BmModelAssist.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBmModelAssistCriteria", e);
			return paginatedList;
		}

	}
}
