package com.huge.ihos.formula.service;


import java.util.List;

import com.huge.ihos.formula.model.UpCost;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface UpCostManager extends GenericManager<UpCost, Long> {
     public JQueryPager getUpCostCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<UpCost> getBycheckPeriodAndDept(String checkPeriod,String dept,Long upItemId,Integer state);
     public String inheritUpCost(Long upItemId,String checkPeriod,String currentPeriod,User user,boolean containAmount,String upItemType);
}