package com.huge.ihos.inout.service;

import com.huge.ihos.inout.model.ChargeItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface ChargeItemManager
    extends GenericManager<ChargeItem, String> {
    public JQueryPager getChargeItemCriteria( final JQueryPager paginatedList );
}