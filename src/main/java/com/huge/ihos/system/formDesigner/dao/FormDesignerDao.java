package com.huge.ihos.system.formDesigner.dao;


import java.util.List;

import com.huge.ihos.system.formDesigner.model.FormDesigner;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the FormDesigner table.
 */
public interface FormDesignerDao extends GenericDao<FormDesigner, String> {
	public JQueryPager getFormDesignerCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}