package com.huge.ihos.system.importdata.service;


import java.util.List;

import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ImportDataDefineDetailManager extends GenericManager<ImportDataDefineDetail, String> {
     public JQueryPager getImportDataDefineDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}