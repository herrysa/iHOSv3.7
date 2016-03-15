package com.huge.ihos.system.oa.bylaw.service;

import java.util.List;

import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ByLawManager
    extends GenericManager<ByLaw, Long> {
    public JQueryPager getByLawCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters, String group_on );

    public List<ByLaw> getByLawsByUser( User user );
}