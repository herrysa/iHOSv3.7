package com.huge.ihos.material.purchaseplan.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.dao.PurchasePlanDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("purchasePlanDetailDao")
public class PurchasePlanDetailDaoHibernate extends GenericDaoHibernate<PurchasePlanDetail, String> implements PurchasePlanDetailDao {

    public PurchasePlanDetailDaoHibernate() {
        super(PurchasePlanDetail.class);
    }
    
    public JQueryPager getPurchasePlanDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("purchaseDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PurchasePlanDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPurchasePlanDetailCriteria", e);
			return paginatedList;
		}

	}
}
