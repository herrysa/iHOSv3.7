package com.huge.ihos.system.systemManager.operateLog.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the OperateLog table.
 */
public interface OperateLogDao
    extends GenericDao<OperateLog, Long> {
    public JQueryPager getOperateLogCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );

}