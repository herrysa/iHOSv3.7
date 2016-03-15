package com.huge.ihos.system.oa.bulletin.service;

import java.util.List;

import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BulletinManager
    extends GenericManager<Bulletin, Long> {
    public JQueryPager getBulletinCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters, String group_on );

    public List<Bulletin> getBulletinsByUser( User user );
}