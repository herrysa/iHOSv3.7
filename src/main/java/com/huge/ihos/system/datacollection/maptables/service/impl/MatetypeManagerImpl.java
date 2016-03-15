package com.huge.ihos.system.datacollection.maptables.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.maptables.dao.MatetypeDao;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.ihos.system.datacollection.maptables.service.MatetypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "matetypeManager" )
public class MatetypeManagerImpl
    extends GenericManagerImpl<Matetype, Long>
    implements MatetypeManager {
    MatetypeDao matetypeDao;

    @Autowired
    public MatetypeManagerImpl( MatetypeDao matetypeDao ) {
        super( matetypeDao );
        this.matetypeDao = matetypeDao;
    }

    public JQueryPager getMatetypeCriteria( JQueryPager paginatedList, String mateTypeId, String costItemId, String costitem, String mateType ) {
        return matetypeDao.getMatetypeCriteria( paginatedList, mateTypeId, costItemId, costitem, mateType );
    }

    public JQueryPager getMatetypeCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters ) {
        return matetypeDao.getMatetypeCriteria( paginatedList, filters );
    }

}