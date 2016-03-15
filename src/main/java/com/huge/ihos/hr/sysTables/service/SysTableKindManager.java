package com.huge.ihos.hr.sysTables.service;


import java.util.List;
import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SysTableKindManager extends GenericManager<SysTableKind, String> {
     public JQueryPager getSysTableKindCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
  	 * TableKind机构树状图
  	 * @param orgCode
  	 * @return List<SysTableKind>
  	 */
  	List<SysTableKind> getFullSysTableKind(String orgCode);
}