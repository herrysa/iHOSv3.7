package com.huge.ihos.kaohe.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InspectBSCColumn table.
 */
public interface InspectBSCColumnDao extends GenericDao<InspectBSCColumn, Long> {
	public JQueryPager getInspectBSCColumnCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<InspectBSCColumn> findByInspectTemplId(String inspectTemplId);
	
	public void delByInspectTemplId(String inspectTemplId);
}