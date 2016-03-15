package com.huge.ihos.system.reportManager.chart.service;

import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface MccKeyDetailManager
    extends GenericManager<MccKeyDetail, String> {
    public JQueryPager getMccKeyDetailCriteria( final String parentId, final JQueryPager paginatedList );
}