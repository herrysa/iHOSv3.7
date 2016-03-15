package com.huge.ihos.pz.linkinfo.service;


import java.util.List;
import com.huge.ihos.pz.linkinfo.model.LinkInfo;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface LinkInfoManager extends GenericManager<LinkInfo, String> {
     public JQueryPager getLinkInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public void changeLinkInfo(String type);
     
     public String getDataSourceId(String type);
}