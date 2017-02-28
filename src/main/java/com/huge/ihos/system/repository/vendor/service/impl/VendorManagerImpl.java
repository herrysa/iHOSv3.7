package com.huge.ihos.system.repository.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.vendor.dao.VendorDao;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.ihos.system.repository.vendor.service.VendorManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("vendorManager")
public class VendorManagerImpl extends GenericManagerImpl<Vendor, String>
		implements VendorManager {
	private VendorDao vendorDao;

	@Autowired
	public VendorManagerImpl(VendorDao vendorDao) {
		super(vendorDao);
		this.vendorDao = vendorDao;
	}

	public JQueryPager getVendorCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		return vendorDao.getVendorCriteria(paginatedList, filters);
	}

	@Override
	public List<Vendor> getVendorsByVendorType(VendorType vendorType) {
		return vendorDao.getVendorsByVendorType(vendorType);
	}

	@Override
	public Vendor save(Vendor object) {
		object.setCnCode(vendorDao.getPyCodes(object.getVendorName()));
		return super.save(object);
	}

}