package com.huge.ihos.accounting.glabstract.service;


import java.util.List;
import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GLAbstractManager extends GenericManager<GLAbstract, String> {
     public JQueryPager getGLAbstractCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public GLAbstract save(GLAbstract glabstract);
}