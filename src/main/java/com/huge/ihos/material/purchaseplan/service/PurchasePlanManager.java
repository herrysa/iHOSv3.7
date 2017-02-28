package com.huge.ihos.material.purchaseplan.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PurchasePlanManager extends GenericManager<PurchasePlan, String> {
     public JQueryPager getPurchasePlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void auditPurchasePlan(List<String> checkIds, Person person,Date date);

	 public void antiAuditPurchasePlan(List<String> cancelCheckIds);

	 public PurchasePlan savePurchasePlan(PurchasePlan purchasePlan,PurchasePlanDetail[] purchasePlanDetails);
	 /**
	  * 根据需求计划生成采购计划
	  * @param needIds 需求计划单的集合
	  * @param storeId 仓库Id
	  * @param sv
	  * @param person
	  * @return
	  */
	 public PurchasePlan createPurchasePlanByNeed(List<String> needIds,String storeId,Person person);

	 public void remove(String removeId);
	 /**
	  * 根据基数生成采购计划
	  * @param conditions
	  * @param sv
	  * @param person
	  * @return
	  */
	 public PurchasePlan createPurchasePlanByBase(Map<String, String> conditions, Person person);
}