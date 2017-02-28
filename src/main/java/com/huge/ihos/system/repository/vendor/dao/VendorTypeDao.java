package com.huge.ihos.system.repository.vendor.dao;

import java.util.List;

import com.huge.dao.BaseTreeDao;
import com.huge.ihos.system.repository.vendor.model.VendorType;

/**
 * An interface that provides a data management interface to the VendorType
 * table.
 */
public interface VendorTypeDao extends BaseTreeDao<VendorType, String> {
	public List<VendorType> getFullVendorTypeTree(String orgCode, String type);

	public List<VendorType> getAllDescendant(String orgCode, String nodeId);

	public List<VendorType> getVendorTypesBySearch(String orgCode,
			String vendorTypeCode, String vendorTypeName, String disabled);
}