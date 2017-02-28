package com.huge.ihos.material.deptapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.model.DeptAppDis;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptAppManager extends GenericManager<DeptApp, String> {
     public JQueryPager getDeptAppCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public DeptApp saveDeptApp(DeptApp deptApp, DeptAppDetail[] deptAppDetails,String businessDate,Person person);

	 public void executePass(Map<String, List<DeptAppDis>> map,Person person);

	 public void endDeptApp(List<DeptApp> deptApps);

	 public void remove(String removeId);

	 public void createFromHis(List<String> idl,Date date,Person person);
}