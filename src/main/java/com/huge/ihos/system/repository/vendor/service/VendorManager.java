package com.huge.ihos.system.repository.vendor.service;


import java.util.List;

import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface VendorManager extends GenericManager<Vendor, String> {
     public JQueryPager getVendorCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<Vendor> getVendorsByVendorType(VendorType vendorType);
}