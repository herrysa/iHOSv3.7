package com.huge.ihos.material.service;


import java.util.List;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvBillNumberSettingManager extends GenericManager<InvBillNumberSetting, Long> {
     public JQueryPager getInvBillNumberSettingCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}