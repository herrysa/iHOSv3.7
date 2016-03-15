package com.huge.ihos.templateDemo.service;

import java.util.List;

import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TemplateDemoManager
    extends GenericManager<GlobalParam, Long> {
    public JQueryPager getGlobalParamCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );
}