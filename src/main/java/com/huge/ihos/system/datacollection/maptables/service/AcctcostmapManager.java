package com.huge.ihos.system.datacollection.maptables.service;

import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface AcctcostmapManager
    extends GenericManager<Acctcostmap, Long> {
    public JQueryPager getAcctcostmapCriteria( final JQueryPager paginatedList, String acctcode, String acctname, String costitemid, String costitem );
}