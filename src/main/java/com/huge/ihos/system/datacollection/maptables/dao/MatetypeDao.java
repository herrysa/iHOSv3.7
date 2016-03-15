package com.huge.ihos.system.datacollection.maptables.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Matetype table.
 */
public interface MatetypeDao
    extends GenericDao<Matetype, Long> {

    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, String mateTypeId, String costItemId, String costitem, String mateType );

    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters );
}