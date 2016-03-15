package com.huge.ihos.inout.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.huge.ihos.inout.model.ChargeType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface ChargeTypeManager
    extends GenericManager<ChargeType, String> {
    public JQueryPager getChargeTypeCriteria( final JQueryPager paginatedList );

    // @Cacheable(value="allChargeTypes")
    public List getAllChargeTypeList();

    public ChargeType save( ChargeType object );
}