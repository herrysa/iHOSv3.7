package com.huge.ihos.system.systemManager.operateLog.service;

import java.util.List;

import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface OperateLogManager
    extends GenericManager<OperateLog, Long> {
    public JQueryPager getOperateLogCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );
}