package com.huge.ihos.system.repository.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.vendor.dao.VendorTypeDao;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.ihos.system.repository.vendor.service.VendorTypeManager;
import com.huge.service.impl.BaseTreeManagerImpl;

@Service("vendorTypeManager")
public class VendorTypeManagerImpl extends
		BaseTreeManagerImpl<VendorType, String> implements VendorTypeManager {
	private VendorTypeDao vendorTypeDao;

	@Autowired
	public VendorTypeManagerImpl(VendorTypeDao vendorTypeDao) {
		super(vendorTypeDao);
		this.vendorTypeDao = vendorTypeDao;
	}

	@Override
	public List<VendorType> getFullVendorTypeTree(String orgCode, String type) {
		return vendorTypeDao.getFullVendorTypeTree(orgCode, type);
	}

	@Override
	public List<VendorType> getAllDescendant(String orgCode, String nodeId) {
		return vendorTypeDao.getAllDescendant(orgCode, nodeId);
	}

	@Override
	public List<VendorType> getVendorTypesBySearch(String orgCode,
			String vendorTypeCode, String vendorTypeName, String disabled) {
		return vendorTypeDao.getVendorTypesBySearch(orgCode, vendorTypeCode,
				vendorTypeName, disabled);
	}

	@Override
	public VendorType save(VendorType object) {
		return super.insertNode(object);
	}

}