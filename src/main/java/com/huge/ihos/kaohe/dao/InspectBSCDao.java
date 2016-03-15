package com.huge.ihos.kaohe.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InspectBSC table.
 */
public interface InspectBSCDao extends GenericDao<InspectBSC, String> {
	public JQueryPager getInspectBSCCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public InspectBSC findByKpiItem(KpiItem kpiItem,String inspectTemplId);
	
	public List<InspectBSC> findByInspectTemplId(String inspectTemplId);
	
	public void deleteByInspectTemplId(String inspectTemplId);
	
	public List<InspectBSC> findByInspectByDept(String inspectTemplId,Department dept);
}