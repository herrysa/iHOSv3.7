package com.huge.ihos.kaohe.service;


import java.util.List;

import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InspectBSCManager extends GenericManager<InspectBSC, String> {
     public JQueryPager getInspectBSCCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public InspectBSC findByKpiItem(KpiItem kpiItem,String inspectTemplId);
     
     public List<InspectBSC> findByInspectTemplId(String inspectTemplId);
     
     public void deleteByInspectTemplId(String inspectTemplId);
     
     public List<InspectBSC> findByInspectByDept(String inspectTemplId,Department dept);
}