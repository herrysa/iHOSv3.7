package com.huge.ihos.system.repository.vendor.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.repository.vendor.dao.VendorDao;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("vendorDao")
public class VendorDaoHibernate extends GenericDaoHibernate<Vendor, String>
		implements VendorDao {

	public VendorDaoHibernate() {
		super(Vendor.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getVendorCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("vendorId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, Vendor.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVendorCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> getVendorsByVendorType(VendorType vendorType) {
		List<Vendor> vendors = null;
		try {
			String hql = "from " + this.getPersistentClass().getSimpleName()
					+ " where vendorTypeId=?";
			vendors = this.getHibernateTemplate().find(hql, vendorType.getId());
		} catch (Exception e) {
			log.error("getVendorsByVendorType", e);
			e.printStackTrace();
		}
		return vendors;
	}

}
