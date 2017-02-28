package com.huge.ihos.system.repository.vendor.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.BaseTreeDaoHibernate;
import com.huge.ihos.system.repository.vendor.dao.VendorTypeDao;
import com.huge.ihos.system.repository.vendor.model.VendorType;

@Repository("vendorTypeDao")
public class VendorTypeDaoHibernate extends
		BaseTreeDaoHibernate<VendorType, String> implements VendorTypeDao {

	public VendorTypeDaoHibernate() {
		super(VendorType.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorType> getFullVendorTypeTree(String orgCode, String type) {
		String hql = "from " + this.getPersistentClass().getSimpleName()
				+ " where orgCode=? and parent_id is not null ";
		if ("disabled".equals(type)) {
			hql += " and disabled = 0 ";
		}
		hql += "ORDER BY vendorTypeCode ASC";
		List<VendorType> vendorTypes = this.getHibernateTemplate().find(hql,
				orgCode);
		return vendorTypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorType> getAllDescendant(String orgCode, String nodeId) {
		String hql = "from "
				+ this.getPersistentClass().getSimpleName()
				+ " where orgCode=? and parent_id=? ORDER BY vendorTypeCode ASC";
		List<VendorType> l = this.getHibernateTemplate().find(hql, orgCode,
				nodeId);
		return l;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getChildrenCount(String nodeId) {
		String hql = "select count(id) from "
				+ this.getPersistentClass().getSimpleName()
				+ " where parent_id= ?";
		List l = this.getHibernateTemplate().find(hql, nodeId);
		int count = ((Long) l.get(0)).intValue();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorType> getVendorTypesBySearch(String orgCode,
			String vendorTypeCode, String vendorTypeName, String disabled) {
		String hql = "from " + this.getPersistentClass().getSimpleName()
				+ " where orgCode = '" + orgCode + "' ";
		if (vendorTypeCode != null) {
			hql += " and vendorTypeCode = '" + vendorTypeCode + "'";
		}
		if (vendorTypeName != null) {
			hql += " and vendorTypeName like '%" + vendorTypeName + "%'";
		}
		if (disabled != null) {
			hql += " and disabled = " + disabled;
		}
		List<VendorType> list = this.getHibernateTemplate().find(hql);
		return list;
	}

}
