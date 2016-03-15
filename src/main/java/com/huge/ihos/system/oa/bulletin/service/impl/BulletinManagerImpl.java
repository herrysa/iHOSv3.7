package com.huge.ihos.system.oa.bulletin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.oa.bulletin.dao.BulletinDao;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "bulletinManager" )
public class BulletinManagerImpl
    extends GenericManagerImpl<Bulletin, Long>
    implements BulletinManager {
    private BulletinDao bulletinDao;

    @Autowired
    public BulletinManagerImpl( BulletinDao bulletinDao ) {
        super( bulletinDao );
        this.bulletinDao = bulletinDao;
    }

    public JQueryPager getBulletinCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String group_on ) {
        return bulletinDao.getBulletinCriteria( paginatedList, filters, group_on );
    }

    @Override
    public List<Bulletin> getBulletinsByUser( User user ) {
        return bulletinDao.getBulletinsByUser( user );
    }
}