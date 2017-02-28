package com.huge.ihos.system.formDesigner.service;


import java.util.List;
import com.huge.ihos.system.formDesigner.model.FormDesigner;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface FormDesignerManager extends GenericManager<FormDesigner, String> {
     public JQueryPager getFormDesignerCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}