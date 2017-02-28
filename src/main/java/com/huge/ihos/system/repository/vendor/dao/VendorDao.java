package com.huge.ihos.system.repository.vendor.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Vendor table.
 */
public interface VendorDao extends GenericDao<Vendor, String> {
	public JQueryPager getVendorCriteria(final JQueryPager paginatedList,
			List<PropertyFilter> filters);

	public List<Vendor> getVendorsByVendorType(VendorType vendorType);
}