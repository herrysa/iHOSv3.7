package com.huge.ihos.hr.hrOrg.service;


import java.util.List;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrOrgManager extends GenericManager<HrOrg, String> {
     public JQueryPager getHrOrgCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 获取当前所有非停用单位
      * @return null：没有对应记录
      */
     public List<HrOrg> getAllAvailable();
     /**
      * 获取当前所有非停用单位的orgCode用逗号间隔的字符串
      * @return null：没有对应记录
      */
     public String getAllAvailableString();
     /**
      * 根据传入的orgCode，得到其下级单位及其自身的单位编码用逗号分割后的字符串
      * @param orgCode
      * @return
      */
	 public String getAllOrgCodesInOrg(String orgCode);
	 /**
	  * 根据传入的orgCode，获取其所有下级
	  * @param orgCode
	  * @return
	  */
	 public List<HrOrg> getAllDescendants(String orgCode);
	 /**
	  * 根据传入的orgCode，获取其所有上级单位
	  * @param orgCode
	  * @return
	  */
	 public List<HrOrg> getAllParentHrOrg(String orgCode);
}