package com.huge.ihos.system.systemManager.globalparam.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Dictionary table.
 */
public interface GlobalParamDao
    extends GenericDao<GlobalParam, Long> {
    public JQueryPager getGlobalParamCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );

    public String getGlobalParamByKey( String key );
}