package com.huge.ihos.templateDemo.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the GlobalParam table.
 */
public interface TemplateDemoDao
    extends GenericDao<GlobalParam, Long> {
    public JQueryPager getGlobalParamCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );
}