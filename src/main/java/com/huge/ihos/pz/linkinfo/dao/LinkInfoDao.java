package com.huge.ihos.pz.linkinfo.dao;


import java.util.List;

import com.huge.ihos.pz.linkinfo.model.LinkInfo;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the LinkInfo table.
 */
public interface LinkInfoDao extends GenericDao<LinkInfo, String> {
	public JQueryPager getLinkInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	
	public void changeLinkInfo(String type);
	
	public String getDateSourceId(String type);
}