package com.huge.ihos.accounting.AssistType.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface AssistTypeManager extends GenericManager<AssistType, String> {
     public JQueryPager getAssistTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<HashMap<String ,String >> getAssits(String table, String assitMark, String id_name, String id_value, List<AssistType> assitTypes);
     public List<AssistType> getAssitTypesByAcct(String acctId);
}