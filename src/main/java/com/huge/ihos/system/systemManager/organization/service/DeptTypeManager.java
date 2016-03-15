package com.huge.ihos.system.systemManager.organization.service;

import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DeptTypeManager
    extends GenericManager<DeptType, String> {
    public JQueryPager getDeptTypeCriteria( final JQueryPager paginatedList );
}