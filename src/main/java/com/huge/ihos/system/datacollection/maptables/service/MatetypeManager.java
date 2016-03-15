package com.huge.ihos.system.datacollection.maptables.service;

import java.util.List;

import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface MatetypeManager
    extends GenericManager<Matetype, Long> {
    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, String mateTypeId, String costItemId, String costitem, String mateType );

    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );

}