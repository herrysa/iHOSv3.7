package com.huge.ihos.system.configuration.code.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.code.model.Code;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Code table.
 */
public interface CodeDao extends GenericDao<Code, String> {
	public JQueryPager getCodeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List isHaveData(Code code);
	public Code getCodeByBdInfo(BdInfo bdInfo,
			HashMap<String, String> environment);
}