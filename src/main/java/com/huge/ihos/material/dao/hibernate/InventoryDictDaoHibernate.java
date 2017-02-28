package com.huge.ihos.material.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InventoryDictDao;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;



@Repository("inventoryDictDao")
public class InventoryDictDaoHibernate extends
		GenericDaoHibernate<InventoryDict, String> implements InventoryDictDao {

	public InventoryDictDaoHibernate() {
		super(InventoryDict.class);
	}


	@SuppressWarnings("rawtypes")
	public JQueryPager getInventoryDictCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {


		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("invId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, InventoryDict.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInventoryDictCriteria", e);
			return paginatedList;
		}

	}


	public void batchEditUpdate(String[] ids, InventoryDict sample) {
		List<InventoryDict> l = new ArrayList<InventoryDict>();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			InventoryDict ivn = this.get(id);
			ivn = this.mergeFromSample(ivn, sample);
			l.add(ivn);

		}
		this.hibernateTemplate.saveOrUpdateAll(l);
	}

	private InventoryDict mergeFromSample(InventoryDict ivn, InventoryDict sample) {
		if (sample.getAgent() != null && !sample.getAgent().trim().equals("")) {
			ivn.setAgent(sample.getAgent());
		}
		if (sample.getInvName() != null && !sample.getInvName().trim().equals("")) {
			ivn.setInvName(sample.getInvName());
		}
		if (sample.getAbc() != null && !sample.getAbc().trim().equals("")) {
			ivn.setAbc(sample.getAbc());
		}
		if (sample.getRefCost() != null) {
			ivn.setRefCost(sample.getRefCost());
		}
		if (sample.getPlanPrice() != null) {
			ivn.setPlanPrice(sample.getPlanPrice());
		}
		if (sample.getEcoBat() != null) {
			ivn.setEcoBat(sample.getEcoBat());
		}
		if (sample.getSafeStock() != null) {
			ivn.setSafeStock(sample.getSafeStock());
		}
		if (sample.getLowLimit() != null) {
			ivn.setLowLimit(sample.getLowLimit());
		}
		if (sample.getHighLimit() != null) {
			ivn.setHighLimit(sample.getHighLimit());
		}
		if (sample.getRefPrice() != null) {
			ivn.setRefPrice(sample.getRefPrice());
		}
		if (sample.getBrandName() != null) {
			ivn.setBrandName(sample.getBrandName());
		}
		if (sample.getStartDate() != null) {
			ivn.setStartDate(sample.getStartDate());
		}
		if (sample.getEndDate() != null) {
			ivn.setEndDate(sample.getEndDate());
		}
		if (sample.getVendor().getVendorId() != null && !sample.getVendor().getVendorId().trim().equals("")) {
			ivn.setVendor(sample.getVendor());
		}
		if (sample.getInventoryType().getId() != null && !sample.getInventoryType().getId().trim().equals("")) {
			ivn.setInventoryType(sample.getInventoryType());
		}

		return ivn;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryDict> getInventoryDictByInventoryType(
			InventoryType inventoryType) {
		List<InventoryDict> inventoryDicts = null;
		try {
			String hql = "from " + this.getPersistentClass().getSimpleName()
					+ " where invType_id=?";
			inventoryDicts = this.getHibernateTemplate().find(hql,inventoryType.getId());
		} catch (Exception e) {
			log.error("getInventoryDictByInventoryType", e);
			e.printStackTrace();
		}
		return inventoryDicts;
	}

}
