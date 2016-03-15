package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface AllotItemDetailManager
    extends GenericManager<AllotItemDetail, Long> {
    public JQueryPager getAllotItemDetailCriteria( final JQueryPager paginatedList, Long allotItemId );
}