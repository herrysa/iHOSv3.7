package com.huge.ihos.system.oa.bylaw.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the ByLaw table.
 */
public interface ByLawDao
    extends GenericDao<ByLaw, Long> {
    public JQueryPager getByLawCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters, String group_on );

    public List<ByLaw> getByLawsByUser( User user );

}