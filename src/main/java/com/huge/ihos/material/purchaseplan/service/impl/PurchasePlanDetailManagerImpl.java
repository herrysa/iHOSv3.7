package com.huge.ihos.material.purchaseplan.service.impl;

import java.util.List;
import com.huge.ihos.material.purchaseplan.dao.PurchasePlanDetailDao;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.service.PurchasePlanDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("purchasePlanDetailManager")
public class PurchasePlanDetailManagerImpl extends GenericManagerImpl<PurchasePlanDetail, String> implements PurchasePlanDetailManager {
    private PurchasePlanDetailDao purchasePlanDetailDao;

    @Autowired
    public PurchasePlanDetailManagerImpl(PurchasePlanDetailDao purchasePlanDetailDao) {
        super(purchasePlanDetailDao);
        this.purchasePlanDetailDao = purchasePlanDetailDao;
    }
    
    public JQueryPager getPurchasePlanDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return purchasePlanDetailDao.getPurchasePlanDetailCriteria(paginatedList,filters);
	}
}