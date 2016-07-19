package com.huge.ihos.system.configuration.businessprocess.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.dao.BusinessProcessStepDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("businessProcessStepDao")
public class BusinessProcessStepDaoHibernate extends GenericDaoHibernate<BusinessProcessStep, String> implements BusinessProcessStepDao {

    public BusinessProcessStepDaoHibernate() {
        super(BusinessProcessStep.class);
    }
    
    public JQueryPager getBusinessProcessStepCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("stepCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessProcessStep.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessProcessStepCriteria", e);
			return paginatedList;
		}

	}
}
