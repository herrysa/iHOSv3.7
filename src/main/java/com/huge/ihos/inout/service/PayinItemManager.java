package com.huge.ihos.inout.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.huge.ihos.inout.model.PayinItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface PayinItemManager
    extends GenericManager<PayinItem, String> {
    public JQueryPager getPayinItemCriteria( final JQueryPager paginatedList );

    // @Cacheable(value="allPayinItems")
    public List getAllPayItems();

}