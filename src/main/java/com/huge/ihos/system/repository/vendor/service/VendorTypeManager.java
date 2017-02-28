package com.huge.ihos.system.repository.vendor.service;

import java.util.List;

import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.service.BaseTreeManager;

public interface VendorTypeManager extends BaseTreeManager<VendorType, String> {

	public List<VendorType> getFullVendorTypeTree(String orgCode, String type);

	public List<VendorType> getAllDescendant(String orgCode, String nodeId);

	public List<VendorType> getVendorTypesBySearch(String orgCode,
			String vendorTypeCode, String vendorTypeName, String disabled);
}