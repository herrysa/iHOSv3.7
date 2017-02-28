package com.huge.ihos.material.service;


import java.util.List;

import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvCheckManager extends GenericManager<InvCheck, String> {
     public JQueryPager getInvCheckCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public InvCheck getInvCheckByCheckNo(String checkNo,String orgCode,String copyCode);
     public InvCheck saveInvCheck(InvCheck invCheck,InvCheckDetail[] invCheckDetails);
     public void removeInvCheck(InvCheck invCheck,List<InvCheckDetail> invCheckDetails);
     public boolean exportCheckInOut(String checkId,Person person);
}