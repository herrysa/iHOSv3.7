package com.huge.ihos.system.systemManager.copy.service;


import java.util.List;

import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface CopyManager extends GenericManager<Copy, String> {
     public JQueryPager getCopyCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<Copy> getRightCopy(Long userId);

	public boolean isNewCopy(String copyCode);

	public Copy getCopyByCode(String copyCode);
}