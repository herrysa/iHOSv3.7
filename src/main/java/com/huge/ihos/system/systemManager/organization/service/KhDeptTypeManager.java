package com.huge.ihos.system.systemManager.organization.service;

import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface KhDeptTypeManager
    extends GenericManager<KhDeptType, String> {
    public JQueryPager getKhDeptTypeCriteria( final JQueryPager paginatedList );
    public KhDeptType getKhDeptTypeByName(String khDeptTypeName);
}