package com.huge.ihos.system.datacollection.maptables.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.maptables.dao.AcctcostmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.ihos.system.datacollection.maptables.service.AcctcostmapManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "acctcostmapManager" )
public class AcctcostmapManagerImpl
    extends GenericManagerImpl<Acctcostmap, Long>
    implements AcctcostmapManager {
    AcctcostmapDao acctcostmapDao;

    @Autowired
    public AcctcostmapManagerImpl( AcctcostmapDao acctcostmapDao ) {
        super( acctcostmapDao );
        this.acctcostmapDao = acctcostmapDao;
    }

    public JQueryPager getAcctcostmapCriteria( JQueryPager paginatedList, String acctcode, String acctname, String costitemid, String costitem ) {
        return acctcostmapDao.getAcctcostmapCriteria( paginatedList, acctcode, acctname, costitemid, costitem );
    }

}