package com.huge.ihos.system.oa.bylaw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.oa.bylaw.dao.ByLawDao;
import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.oa.bylaw.service.ByLawManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "byLawManager" )
public class ByLawManagerImpl
    extends GenericManagerImpl<ByLaw, Long>
    implements ByLawManager {
    private ByLawDao byLawDao;

    @Autowired
    public ByLawManagerImpl( ByLawDao byLawDao ) {
        super( byLawDao );
        this.byLawDao = byLawDao;
    }

    public JQueryPager getByLawCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String group_on ) {
        return byLawDao.getByLawCriteria( paginatedList, filters, group_on );
    }

    @Override
    public List<ByLaw> getByLawsByUser( User user ) {
        return byLawDao.getByLawsByUser( user );
    }
}