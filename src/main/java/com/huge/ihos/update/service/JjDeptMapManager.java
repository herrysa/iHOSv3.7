package com.huge.ihos.update.service;


import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.update.model.JjDeptMap;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface JjDeptMapManager extends GenericManager<JjDeptMap, Integer> {
     public JQueryPager getJjDeptMapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
 	 * 根据操作员ID得到部门列表
 	 * @param personId
 	 * @return
 	 */
 	public List<Department> getByOperatorId(String personId);
 	/**
 	 * 得到In条件
 	 * @param personId
 	 * @return
 	 */
 	public String getDeptIdInS(String personId);
 	
 	public String[] getDeptIdsByPerson(String personId);
 	public List<Department> getAllDept();
}