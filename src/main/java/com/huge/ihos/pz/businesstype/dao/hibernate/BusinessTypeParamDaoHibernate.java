package com.huge.ihos.pz.businesstype.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeParamDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("businessTypeParamDao")
public class BusinessTypeParamDaoHibernate extends GenericDaoHibernate<BusinessTypeParam, String> implements BusinessTypeParamDao {

    public BusinessTypeParamDaoHibernate() {
        super(BusinessTypeParam.class);
    }
    
    public JQueryPager getBusinessTypeParamCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("paramId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessTypeParam.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessTypeParamCriteria", e);
			return paginatedList;
		}

	}
}
