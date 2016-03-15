package com.huge.ihos.system.oa.bulletin.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Bulletin table.
 */
public interface BulletinDao
    extends GenericDao<Bulletin, Long> {
    public JQueryPager getBulletinCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters, String group_on );

    public List<Bulletin> getBulletinsByUser( User user );

}