package com.huge.ihos.material.typeno.service;


import java.util.List;

import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvTypeNoManager extends GenericManager<InvTypeNo, Long> {
     public JQueryPager getInvTypeNoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public String getTypeByNo(String no,String orgCode,String copyCode);
}