package com.huge.ihos.hr.hrPerson.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrPersonSnap table.
 */
public interface HrPersonSnapDao extends GenericDao<HrPersonSnap, String> {
	public JQueryPager getHrPersonSnapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<String> getHisSnapIds(String timestamp);
	
	public HrPersonSnap getMaxHrPersonSnap(String personId);
	
	public Map<String, HrPersonSnap> getPersonIdPersonMap(String timestamp);
	
//	public String importHrPersonFromExcel(String filePath)throws ImportException;
}