package com.huge.ihos.material.service;


import java.util.List;
import com.huge.ihos.material.model.InvUse;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvUseManager extends GenericManager<InvUse, String> {
     public JQueryPager getInvUseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<InvUse> getAllByCO(String copycode,String orgcode);
}