package com.huge.ihos.kaohe.service;


import java.util.List;

import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InspectBSCColumnManager extends GenericManager<InspectBSCColumn, Long> {
     public JQueryPager getInspectBSCColumnCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<InspectBSCColumn> findByInspectTemplId(String inspectTemplId);
     
     public void delByInspectTemplId(String inspectTemplId);
}