package com.huge.ihos.system.configuration.businessprocess.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.ihos.system.configuration.businessprocess.dao.BusinessProcessDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("businessProcessDao")
public class BusinessProcessDaoHibernate extends GenericDaoHibernate<BusinessProcess, String> implements BusinessProcessDao {

    public BusinessProcessDaoHibernate() {
        super(BusinessProcess.class);
    }
    
    public JQueryPager getBusinessProcessCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("processCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessProcess.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessProcessCriteria", e);
			return paginatedList;
		}

	}
}
