package com.huge.ihos.hr.sysTables.dao;


import java.util.List;

import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SysTableKind table.
 */
public interface SysTableKindDao extends GenericDao<SysTableKind, String> {
	public JQueryPager getSysTableKindCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<SysTableKind> getFullSysTableKind(String orgCode);
}