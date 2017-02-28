package com.huge.ihos.material.order.service;


import java.util.List;
import com.huge.ihos.material.order.model.ImportOrderLogDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ImportOrderLogDetailManager extends GenericManager<ImportOrderLogDetail, String> {
     public JQueryPager getImportOrderLogDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}