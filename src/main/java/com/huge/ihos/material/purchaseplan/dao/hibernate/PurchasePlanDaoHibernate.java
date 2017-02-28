package com.huge.ihos.material.purchaseplan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.ihos.material.purchaseplan.dao.PurchasePlanDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("purchasePlanDao")
public class PurchasePlanDaoHibernate extends GenericDaoHibernate<PurchasePlan, String> implements PurchasePlanDao {

    public PurchasePlanDaoHibernate() {
        super(PurchasePlan.class);
    }
    
    public JQueryPager getPurchasePlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("purchaseId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PurchasePlan.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPurchasePlanCriteria", e);
			return paginatedList;
		}

	}
}
